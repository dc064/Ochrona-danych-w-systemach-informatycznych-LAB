from sys import argv

key = argv[1]

fname = argv[2];
f = open(fname, "r")
text = f.read()

S = list(range(0, 256))
j = 0
textLength = len(text)

for i in S:
    j = (j + S[i] + ord(key[i % len(key)])) % 256

    tmp = S[j]
    S[j] = S[i]
    S[i] = tmp

i = 0
j = 0

for n in range(textLength):
    i = (i + 1) % 256
    j = (j + S[i]) % 256

    tmp = S[i]
    S[j] = S[i]
    S[i] = tmp

    K = S[(S[i] + S[j]) % 256]
    cipherChar = chr(K ^ ord(text[n]))
    print(cipherChar, end = '')

