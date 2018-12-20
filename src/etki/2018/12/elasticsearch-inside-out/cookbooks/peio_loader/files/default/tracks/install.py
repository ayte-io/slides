#!/usr/bin/env python

import os
import shutil

directory = os.path.dirname(os.path.realpath(__file__))
source = os.path.join(directory, '_shared')

for entry in os.listdir(directory):
    path = os.path.join(directory, entry)
    if os.path.isfile(path) or entry == '_shared' or entry == '.' or entry == '..':
        continue
    target = os.path.join(path, '_shared')
    if os.path.isdir(target):
        shutil.rmtree(target)
    shutil.copytree(source, target)
