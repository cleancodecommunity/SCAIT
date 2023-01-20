import os
import sys
import pytest

sys.path.append('./src/main/java/python_control')
from main import main

@pytest.fixture(autouse=True)
def fixture():
    """Basic fixture for tests"""
    python_file_paths = []
    repository_paths = [
        './src/main/java/python_control'
        './tests/python/adanet',
        './tests/python/Auto-PyTorch',
        './tests/python/autogluon',
        './tests/python/autokeras',
    ]

    for path in repository_paths:
        for subdir, dirs, files in os.walk(path):
            for f in files:
                if len(f) > 3 and f[-3:] == '.py':
                    fileName = os.path.join(subdir, f)
                    with open(fileName, 'r', encoding='utf8') as file:
                        data = file.read()
                    python_file_paths.append((fileName, data))

    yield python_file_paths


def test_train_text_models_validation(fixture):
    """Run code analysis on all testing Python files"""
    python_file_paths = fixture
    for fileName, data in python_file_paths:
        main(fileName, data)
