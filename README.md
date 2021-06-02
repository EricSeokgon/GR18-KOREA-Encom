# GR18-KOREA-Encom
## Overview
Meraki-for-SmartCity is a management dashboard with a missing child alert feature on top of it. Its goal is to build an additional value on Cisco Meraki product for governments and public organizations to have digitized IT work process. Meraki-for-SmartCity is designed for monitoring Meraki APs, presenting visualized analysis from Meraki API, sending an real-time Webex alert message when the missing child is found around the Meraki APs. The flexibility of web dashboard and scalability of Webex alert message brings Smart City available on our laptop, or even on our phone.

## The Problem to Solve 
Finding a missing child in crowded area such as famous beach, tourist attractions, and shopping center is a complicated problem. It requires human resource, time, and effort. However, most of them already have implemented APs for public Wi-Fi so we can utilize the existing infrastructure to resolve this problem.

## Solution Diagram
![Diagram_FaceRecog](https://user-images.githubusercontent.com/73694660/120415183-146b0200-c396-11eb-8342-ecb76900dad1.png)

## Software Architecture
![Diagram_SoftwareArch](https://user-images.githubusercontent.com/73694660/120415190-1765f280-c396-11eb-84b1-30433788c359.png)

## How it works
It displays the webcam video in real time with person’s names tagged on the video.
* Camera.py – Checks webcam and opens a window to display it
* Face_recog.py – Recognizes the faces on webcam frame by comparing face object with the pictures in ‘knowns’ folder
* Live_streaming.py – Send video over network http://{{ip_address}}:5000/. Face recognition can be displayed via web with this code.
### Usage
```
$ python camera.py
$ python face_recog.py
$ python live_streaming.py
```
Put picture with the person’s face in ‘knowns’ directory. Change the file name as the person’s name like ‘john.jpg’. Then run ‘python face_recog.py’ or ‘python live_streaming.py’ to send video over network.

## Related Projects
https://github.com/ukayzm/opencv/tree/master/face_recognition
