import timeit
import bcrypt
from timeit import default_timer as timer
from passlib.hash import md5_crypt
from argon2 import PasswordHasher
import string
import hashlib

file = open('words.txt', 'r')
words = file.readlines()

#md5

start = timer()

for word in words:
    current = md5_crypt.hash(word)

end = timer()

md5time = end - start

#bcrypt

start = timer()

salt = bcrypt.gensalt()

for word in words:
    current = bcrypt.hashpw(b'{word}', salt)

end = timer()

bcrypttime = end - start

#sha256

start = timer()

for word in words:
    current = hashlib.sha256(word.encode('ascii'))

end = timer()

sha256time = end - start

#argon2

argon2 = PasswordHasher()

start = timer()

for word in words:
    current = argon2.hash(word)

end = timer()

argon2time = end - start

f = open("time_results.txt", "a")
f.write(str(md5time) + '\n')
f.write(str(bcrypttime) + '\n')
f.write(str(sha256time) + '\n')
f.write(str(argon2time) + '\n')
f.close()
