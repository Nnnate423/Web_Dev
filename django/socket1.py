import socket
# create socket
mysock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# connect to a server
mysock.connect(('data.pr4e.org', 80))
# \r\n {HEADER} \r\n this time no header so \r\n\r\n
cmd = 'GET http://data.pr4e.org/page1.htm HTTP/1.0\r\n\r\n'.encode()
# send http request
mysock.send(cmd)

while True:
    #blocking call until 512 bits? for the trans done
    data = mysock.recv(512)
    if len(data) < 1:
        break
    # data comes in as UTF-8 and we wants to print unicode
    print(data.decode(),end='')

mysock.close()