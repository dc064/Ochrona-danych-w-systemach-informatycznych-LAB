from Cryptodome.Random import get_random_bytes
from BMPcrypt import encrypt_data
from Crypto.Cipher import AES
import math

def entropy(data):
    N = len(data)
    all = 0
    n = {}
        #uzupelniamy slownik z naszymi licznosciami
    for character in data:
        if character in n:
            n[character] = n[character] + 1
        else:
            n[character] = 1
        all = all + 1

    H = 0

    for g in n.keys():
        pi = n[g] / all
        H = H + (pi * math.log2(pi))
    H *= -1
    return H

filecbc = 'demo24cbc.bmp'
fileebc = 'demo24ebc.bmp'

img_cbc = open(filecbc, "rb")
img_ebc = open(fileebc, "rb")
dataC = img_cbc.read()
dataE = img_ebc.read()

encrypt_data("demo24cbc.bmp", "CBC", get_random_bytes(16))
encrypt_data("demo24ebc.bmp", "ECB", get_random_bytes(16))

CBC = open("demo24cbc_CBC_encrypted.bmp", "rb")
ECB = open("demo24ebc_ECB_encrypted.bmp", "rb")

c = CBC.read()
e = ECB.read()

print("Entropy of CBC: ", entropy(c))
print("Entropy of ECB: ", entropy(e))
