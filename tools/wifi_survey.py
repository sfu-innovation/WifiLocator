import os
import re
import sys
import time

# ----------------------
# wifi_survey.py
# samples currently visible routers BSSIDs and Signal strength.
#
# when run from the command line, the program takes one argument
# which is the filename to save the data to. Important: if the file already
# exsists, it will be over written.
# python wifi_survey.py <filename/zonename>
#
# Note: this script will on work on windows because it relies on 
# the windows netsh command
# ----------------------

# Parameters
samples = 1
network = "(SFUNET)|(eduroam)"
area = "zone0"

if (len(sys.argv) > 1):
	area = sys.argv[1]
validnetwork = False
f = open(area + '.txt', 'a')

for i in range(samples):
	print "Waiting for input"
	chr = ""
	while chr != "\n":
		chr = sys.stdin.read(1)
	
	result = os.popen("netsh wlan show networks mode=Bssid").read()
	resultlines = result.split("\n")
	f.write("--TEST--\n")
	for line in resultlines:
		line = re.sub(" ", "", line)
		line = re.sub(":", ",", line, 1)
		if re.search("BSSID", line) and validnetwork:
			f.write(line + ",")
		elif re.search("^SSID", line):
			validnetwork = re.search(network, line.split(",")[1])
			if validnetwork:
				f.write(re.sub("[0-9]", "", line, 0) + "\n")
		elif re.search("Signal", line) and validnetwork:
			f.write(line + "\n")
	
f.close()
#print "Script Exited Successfully"