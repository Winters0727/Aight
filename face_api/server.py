from flask import Flask
from flask_socketio import SocketIO, emit
from flask_cors import CORS
import cv2
import numpy as np
import matplotlib.pyplot as plt
from PIL import Image
import torch
import torch.nn as nn
import torch.nn.functional as F
import os
from collections import deque, defaultdict
from torch.autograd import Variable
import seaborn as sns
import json

import transforms as transforms
from skimage import io
from skimage.transform import resize
from models import *

def rgb2gray(rgb):
    return np.dot(rgb[...,:3], [0.299, 0.587, 0.114])

def video_analysis(username, video_type):

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
        vidcap = cv2.VideoCapture('./videos/{0}/{1}{2}.{3}'.format(name, name, video_index, video_type))
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

    def cal_point(result_list):
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

    folder_index = video_to_image(username)

    if folder_index:
        result = capture_emotion(folder_index, username)
        
        point, point_list, emo_list = cal_point(result)
        result_data = {
            'point_list' : point_list
        }
        emit("res", {'data' : json.dumps(result_data)})

app = Flask(__name__)
CORS(app, resources={r"/*": {"origins": "*"}})

app.config['SECRET_KEY'] = 'secret_key'
socketio = SocketIO(app, cors_allowed_origins="*")

@socketio.on('connect')
def connect():
    print('connect')

@socketio.on('disconnect')
def disconnect():
    print('client disconnect')

@socketio.on('cvdata')
def cvdata(data):

    data = data['data']
    length = len(data.keys())
    data = np.array([data[str(index)] for index in range(length)])
    data = data.reshape(320,240,4)

    gray = rgb2gray(data)

    gray = resize(gray, (48,48), mode='symmetric').astype(np.uint8)

    img = gray[:, :, np.newaxis]

    img = np.concatenate((img, img, img), axis=2)
    img = Image.fromarray(img)
    inputs = transform_test(img)

    class_names = ['Angry', 'Disgust', 'Fear', 'Happy', 'Sad', 'Surprise', 'Neutral']

    ncrops, c, h, w = np.shape(inputs)

    inputs = inputs.view(-1, c, h, w)
    inputs = Variable(inputs, volatile=True)
    outputs = net(inputs)

    outputs_avg = outputs.view(ncrops, -1).mean(0)  # avg over crops

    score = F.softmax(outputs_avg)
    _, predicted = torch.max(outputs_avg.data, 0)

    emotion_list = list()

    for i in range(len(class_names)):
        emotion_list.append((class_names[i], score.data.cpu().numpy()[i]))
    emotion_list.sort(key=lambda x:x[1], reverse=True)
    result_data = {'emotion' : {key:str(val) for (key,val) in emotion_list}, 'path' : ''}
    emit("res", {'data' : json.dumps(result_data)})

@socketio.on('my event')
def handle_my_custum_event(json, methods=['GET', 'POST']):
    print('received json' + str(json))

@socketio.on('uploadFile')
def uploadFile(data):
    username, video_file, video_type = data['data']['uid'], data['data']['file'], data['data']['type'].split('/')[1]
    user_list = os.listdir('./videos')
    if username not in user_list:
        os.mkdir('./videos/{0}'.format(username))
    video_count = len(os.listdir('./videos/{0}'.format(username)))+1
    with open('./videos/{0}/{1}{2}.{3}'.format(username, username, video_count, video_type), 'wb') as f:
        f.write(video_file);
    user_path = os.path.abspath('./videos/{0}'.format(username))
    emit("success", {'data':'{0}/{1}{2}.{3}'.format(username, username, video_count, video_type)})
    video_analysis(username, video_type)

cut_size = 44

transform_test = transforms.Compose([
transforms.TenCrop(cut_size),
transforms.Lambda(lambda crops: torch.stack([transforms.ToTensor()(crop) for crop in crops])),
])

device = torch.device('cpu')
net = VGG('VGG19')
checkpoint = torch.load(os.path.join('FER2013_VGG19', 'PrivateTest_model.t7'), map_location=device)
net.load_state_dict(checkpoint['net'])
net.eval()


if __name__ == '__main__':
    app.debug = True
    print("server start!")
    socketio.run(app, host='0.0.0.0', port=8000)