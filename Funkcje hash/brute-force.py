import string
from itertools import combinations
from itertools import product
from string import ascii_lowercase
import passlib
from argon2 import PasswordHasher

ph = PasswordHasher()
keywords = [''.join(i) for i in product(ascii_lowercase, repeat = 2)]

hashText = "$argon2id$v=19$m=65536,t=3,p=4$4Vzr3bvXWuvdmzMG4PxfCw$NWNunMWdo0ugkWWsL8Z+sdMKnDcJp0vDfMkr30Lmpd4"

split = hashText.split("$")

for key in keywords:
    try:
        if(ph.verify(hashText, key)):
            print('Found password: ', key)
            break
    except Exception:
        pass
