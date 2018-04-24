
# Usage:
#   1) Copy the contents of the Android Studio console into file 'logcat.txt'
#   2) Run the following command:
#        $ python filter.py

import sys
import os.path

def loadFields(fileName, tagFieldPos):
    # Currently the Android Studio logcat outputs the description immediately
    # after the tag; it can be configured here if it needed but ALWAYS it must
    # a located after the position of the tag.
    descriptionFieldPos = tagFieldPos + 1
    assert (descriptionFieldPos > tagFieldPos)

    """ Returns a list of tuplas with the specified fields."""
    result=[]

    with open(fileName, 'rU') as inFile:
       lineNumber = 0
       for line in inFile:
           lineNumber += 1 
           cleanLine = line.strip()

           if line != '':
              fieldsList = cleanLine.split()
              fieldsNumber = len(fieldsList)

              if fieldsNumber >= descriptionFieldPos:
                 tag = fieldsList[tagFieldPos]

                 # Collect all the fields containing the description into
                 # a single string.

                 description = ""
                 for J in range(descriptionFieldPos, fieldsNumber):
                    description += fieldsList[J] + ' '

                 # Append this tupla
                 thisResult = (tag, description)
                 result.append(thisResult)

    return result


def filterList(fieldsList, text):
    """ fieldsList is a list of 2-component tuplas. Returns a list
        containing only those tuplas whose first component contains
        the given text. """

    resultList = []
    for elem in fieldsList:
        tagName = elem[0]

        if tagName.find(text) != -1:
           resultList.append(elem)

    return resultList


def outputList(fieldsList):
    # Local configuration variables
    ColumnWidth=6
    Debug=False

    # Initialize a string with spaces and vertical bars; used to generate
    # the left margin of the output
    Spaces=''
    for J in range (1,4):
        Spaces = Spaces + ' ' * (ColumnWidth - 2) + '| '

    for elem in fieldsList:
        tagName = elem[0]
        msgName = elem[1]
        leftMargin = -1

        if tagName.find('Mediator') != -1:
           leftMargin = 0         

        elif tagName.find('Model') != -1:
           leftMargin = 2 * ColumnWidth

        elif tagName.find('Presenter') != -1:
           leftMargin = 1 * ColumnWidth

        elif tagName.find('View') != -1:
           leftMargin = 2 * ColumnWidth

        if leftMargin >=0:
           tagNameWithoutPrefix = tagName[2:] # Removing prefix 'D/'
           print(Spaces[:leftMargin] + tagNameWithoutPrefix + ' ' + msgName)

        elif Debug:
           print (elem)
    return


# Main
# ----

if __name__ == '__main__':

   # Check if this script was invoked with a filename

   if (len(sys.argv)) > 2 or (len(sys.argv) > 1 and sys.argv[1] == "-h"):
      print('Syntax: ' + sys.argv[0] + ' [filename | -h]');
      print("  filename: Name of the input file. The filename 'logcat.txt' is")
      print("            used by default if no filename is specified.")
      print("        -h: Output this help description.")
      sys.exit(-1)

   elif (len(sys.argv)) > 1:
      inputFilename = sys.argv[1]

   else:
      inputFilename = 'logcat.txt'

   if not os.path.isfile(inputFilename):
      print('error: file ' + inputFilename + ' not found.')
   else:
      initialList = loadFields(inputFilename, tagFieldPos=3)

      filteredList = filterList(initialList, 'D/')
      outputList(filteredList)
