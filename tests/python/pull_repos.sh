#!/bin/bash

echo Cloning repositories...

# Clone repository or pull if already existing
if cd ./tests/python/adanet; then echo Adanet: && git pull; else mkdir -p ./tests/python/adanet && git clone https://github.com/tensorflow/adanet.git ./tests/python/adanet; fi
cd ../../../
if cd ./tests/python/autokeras; then echo AutoKeras: && git pull; else mkdir -p ./tests/python/autokeras && git clone https://github.com/keras-team/autokeras.git ./tests/python/autokeras; fi
cd ../../../
if cd ./tests/python/autogluon; then echo AutoGluon: && git pull; else mkdir -p ./tests/python/autogluon && git clone https://github.com/autogluon/autogluon.git ./tests/python/autogluon; fi
cd ../../../
if cd ./tests/python/Auto-PyTorch; then echo Auto-PyTorch: && git pull; else mkdir -p ./tests/python/Auto-PyTorch && git clone https://github.com/automl/Auto-PyTorch.git ./tests/python/Auto-PyTorch; fi
cd ../../../

echo Script completed