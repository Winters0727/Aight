import os
from collections import deque, defaultdict

import numpy as np
import matplotlib.pyplot as plt
import cv2
from PIL import Image
import torch
import torch.nn as nn
import torch.nn.functional as F
import os
from torch.autograd import Variable
import seaborn as sns

import transforms as transforms
from skimage import io
from skimage.transform import resize
from models import *

cut_size = 44

transform_test = transforms.Compose([
    transforms.TenCrop(cut_size),
    transforms.Lambda(lambda crops: torch.stack([transforms.ToTensor()(crop) for crop in crops])),
])

def rgb2gray(rgb):
    return np.dot(rgb[...,:3], [0.299, 0.587, 0.114])

def video_to_image(name):
    video_folders = os.listdir('./videos')
    if not name in video_folders:
        print('There is no video!')
        return 0
    
    image_folders = os.listdir('./images')
    if not name in image_folders:
        os.mkdir('./images/{0}'.format(name))
    name_image_folders = os.listdir('./images/{0}'.format(name))
    folder_index = len(name_image_folders)+1
    os.mkdir('./images/{0}/{1}'.format(name, folder_index))

    name_video_folders = os.listdir('./images/{0}'.format(name))
    video_index = len(name_video_folders)
    vidcap = cv2.VideoCapture('./videos/{0}/{1}{2}.mp4'.format(name, name, video_index))
    count = 0
    while vidcap.isOpened():
        ret, image = vidcap.read()

        if ret:
            if(int(vidcap.get(1)) % 20 == 0):
                cv2.imwrite("./images/{0}/{1}/frame{2}.jpg".format(name, folder_index, count), image)
                count += 1
        else:
            break
    vidcap.release()

    return folder_index

def capture_emotion(folder_index, name):
    images = os.listdir('./images/{0}/{1}'.format(name, folder_index))

    result_list = deque()

    for index in range(len(images)):
        raw_img = io.imread('images/{0}/{1}/frame{2}.jpg'.format(name, folder_index, index))
        gray = rgb2gray(raw_img)
        gray = resize(gray, (48,48), mode='symmetric').astype(np.uint8)

        img = gray[:, :, np.newaxis]

        img = np.concatenate((img, img, img), axis=2)
        img = Image.fromarray(img)
        inputs = transform_test(img)

        class_names = ['Angry', 'Disgust', 'Fear', 'Happy', 'Sad', 'Surprise', 'Neutral']

        ncrops, c, h, w = np.shape(inputs)

        inputs = inputs.view(-1, c, h, w)
        # inputs = inputs.cuda()
        inputs = Variable(inputs, volatile=True)
        outputs = net(inputs)

        outputs_avg = outputs.view(ncrops, -1).mean(0)  # avg over crops

        score = F.softmax(outputs_avg)
        _, predicted = torch.max(outputs_avg.data, 0)

        emotion_list = list()
        
        for i in range(len(class_names)):
            emotion_list.append((class_names[i], score.data.cpu().numpy()[i]))
        emotion_list.sort(key=lambda x:x[1], reverse=True)
        emotion_list = deque(emotion_list)
        
        result_list.append(emotion_list)
    
    return result_list

def cal_point_one(result_list):
    point = 50
    point_list = list()
    emo_list = list()
    while result_list:
        result = result_list.popleft()
        most_emotion = result.popleft()
        emotion, percentage = most_emotion
        emo_list.append(emotion)
        if emotion == 'Happy':
            point += 1.5
        elif emotion == 'Surpirse':
            point += 0.5
        elif emotion == 'Fear':
            point -= 0.5
        elif emotion == 'Sad':
            point -= 1.5
        elif emotion == 'Angry':
            point -= 2.5
        else:
            pass
        point_list.append(point)
    return point, point_list, emo_list

# def cal_point_two(result_list):
#     most_emo_list = list()
#     percentage_list = list()
#     emo_dict = defaultdict(int)
#     count = 0
#     while result_list:
#         count += 1
#         result = result_list.popleft()
#         temp_percentage_list = list()
#         for emotion, percentage in result:
#             emo_dict[emotion] += percentage
#             temp_percentage_list.append((emotion, emo_dict[emotion]/count))
#         temp_percentage_list.sort(key=lambda x : x[1], reverse=True)
#         most_emotion, most_percentage = temp_percentage_list[0]
#         most_emo_list.append(most_emotion)
#         percentage_list.append(temp_percentage_list)
#     return most_emo_list, percentage_list

device = torch.device('cpu')
net = VGG('VGG19')
checkpoint = torch.load(os.path.join('FER2013_VGG19', 'PrivateTest_model.t7'), map_location=device)
net.load_state_dict(checkpoint['net'])
net.eval()

# net = VGG('VGG19')
# checkpoint = torch.load(os.path.join('FER2013_VGG19', 'PrivateTest_model.t7'))
# net.load_state_dict(checkpoint['net'])
# net.cuda()
# net.eval()

while True:
    name = input('input name : ')
    if not name:
        break
    folder_index = video_to_image(name)
    if folder_index:
        result = capture_emotion(folder_index, name)
        
        point, point_list, emo_list = cal_point_one(result)
        print(point)
        print(point_list)
        print(emo_list)

        # most_emo_list, percentage_list = cal_point_two(result)
        # print(percentage_list)
        # print(most_emo_list)

        # sns.set_theme(style="darkgrid")
        # sns.lineplot(x=range(1,len(point_list)+1), y=point_list)
        # plt.xlabel='Time'
        # plt.ylabel='Point'
        # plt.show()