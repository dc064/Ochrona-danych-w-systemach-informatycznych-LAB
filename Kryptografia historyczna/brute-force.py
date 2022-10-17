from arc4 import ARC4
import math
import string
from itertools import combinations

def all_keys_combination(letters):
    #letters = list(string.ascii_lowercase)
    combinations = []
    for comb in combinations(letters, 3):
        combinations.append(''.join(comb))
    return combinations

def generateCombination(lots, length):
    combilist = []
    for comb in combinations(lots, length):
        combilist.append(''.join(comb))
    return combilist

def entropy_calculator(data):
    n = {}
    all = 0
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

def decrypter(key, cipher):
    arc4 = ARC4(str.encode(key))
    decrypted = arc4.decrypt(cipher)
    return decrypted

letters = list(string.ascii_lowercase)

all_keys = generateCombination(letters, 3)

fname = 'crypto.rc4'
f = open(fname, "rb")
cipher = f.read()

for i in all_keys:
    string = decrypter(i, cipher)
    entropy = entropy_calculator(string)
    if(entropy < 5):
        print(string)
        print('key: ',i)
        break


