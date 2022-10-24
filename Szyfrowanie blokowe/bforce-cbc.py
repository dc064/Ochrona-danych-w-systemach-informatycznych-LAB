from PIL import Image
from Crypto.Protocol.KDF import PBKDF2
from Crypto.Cipher import AES
from Crypto.Random import get_random_bytes
from BMPcrypt import encrypt_full
import string
from itertools import combinations
from Cryptodome.Util.Padding import pad
from sys import argv
from itertools import product
from string import ascii_lowercase

keywords = [''.join(i) for i in product(ascii_lowercase, repeat = 3)]
def nullpadding(data, length=16):
    return data + b"\x00"*(length-len(data) % length)

iv = 'a' * 16
input_filename = argv[1]
img_in = open(input_filename, "rb")
data = img_in.read()
nullpadded = nullpadding(data)
data_len = len(data)

for key in keywords:
    ckey = PBKDF2(key.encode(encoding='ascii'), b"abc")
    aes = AES.new(ckey, AES.MODE_CBC, str.encode(iv))
    
    decrypted = aes.decrypt(nullpadded)

    if(chr(decrypted[0]) == 'B' and chr(decrypted[1]) == 'M'):
        print("found key: ", key)

        img_out = open('decryptedImage.bmp', 'wb')
        img_out.write(decrypted[:data_len])
        img_out.close()

        break

