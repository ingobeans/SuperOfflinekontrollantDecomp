import socket,json

UDP_IP = "127.0.0.1"
UDP_PORT = 6789

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.bind((UDP_IP, UDP_PORT))

print("launching :)")

def send_response(data:dict,addr):
    global sock
    sock.sendto(bytes(json.dumps(data),encoding="utf-8"),addr)

while True:
    data, addr = sock.recvfrom(1024) 
    data = data.decode("utf-8")
    data = json.loads(data)
    print ("Received message:", data)

    if data["type"] == 292: # are you there?
        send_response({"messageType":292,"responseOK":True},addr)
    if data["type"] == 109: # register user
        send_response({"messageType":109,"responseOK":True},addr)
    if data["type"] == 355: # submit exam
        send_response({"messageType":355,"responseOK":True},addr)