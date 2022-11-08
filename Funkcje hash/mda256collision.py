import sys
import hashlib
import random
import string

def get_random_string(length):
    letters = string.ascii_lowercase
    result_str = ''.join(random.choice(letters) for i in range(length))
    return result_str


hashes = {}
while True:
    new_string = get_random_string(random.randint(3, 20))
    
    if new_string in hashes:
        print('znaleziono ', new_string)
        continue
    
    hash_text = hashlib.sha256(new_string.encode('ascii'))
    hash_code = hash_text.hexdigest()
    key = hash_code[0:10]
    
    if key in hashes:
        if new_string == hashes[key][0]:
            continue
        print(f"FIND SOLUTION IN NUMBER OF {len(hashes) + 1}")
        print(f"Hash 1 : {hashes[key][1]} , text : {hashes[key][0]}")
        print(f"Hash 1 : {hash_code} , text : {new_string}")
        break
    hashes[key] = (new_string, hash_code)
