# the script is for python 3
import os
import glob


textToFind ='<h2 dir="RTL">'
index = 1


dir_path = os.path.dirname(os.path.realpath(__file__))
style = '<style>.left {float: left;padding: 10px;}</style>'

for fileName in glob.iglob(dir_path + '**/*', recursive=True):
    if not fileName.endswith('.py') :
        with open(fileName, 'r', encoding="utf8") as f:
            content = f.readlines()
            newContent = style
            for line in content:
                if textToFind == line.strip() :
                    line ='<h2 dir="RTL"> <div class ="left"><img type="button" id="{}" onclick="audio.performClick(id);" src="./video-play-3-128.png" width="36px"; height="36px";></img></div>\n'.format(index)
                    index += 1
                elif textToFind in line :
                    line = line.replace('<h2 dir="RTL">', '<h2 dir="RTL"> <div class ="left"><img type="button" id="{}" onclick="audio.performClick(id);" src="./video-play-3-128.png" width="36px"; height="36px";></img></div>'.format(index))
                    index += 1
                newContent += line
            print("number of text insertions for this file " + str(index))
            with open(fileName, 'w', encoding="utf8") as file:
                file.write(newContent)
            index=1