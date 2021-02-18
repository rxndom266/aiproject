import aiml
from os.path import dirname, join
root = dirname(__file__)
print("*"+root)
greet = join(dirname(__file__), "greetings.xml")
def response(message):
    kernel = aiml.Kernel()
    kernel.learn(greet)
    res = kernel.respond(message)
    return res