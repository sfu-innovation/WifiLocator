# a python script to repeatedly call the other python script

import sys
import os
import time

samples = 2
area = "zone0"

if (len(sys.argv) > 1):
	area = sys.argv[1]

for i in range(samples):
	os.system("python wifi_survey.py " + area)
	print "Taking Sample " + str(i) + " ..."
	
	# sleep to allow netsh time to refresh
	if i < samples-1:
		print "0% complete"
		time.sleep(4)
		print "20% complete"
		time.sleep(4)
		print "40% complete"
		time.sleep(4)
		print "60% complete"
		time.sleep(4)
		print "80% complete"
		time.sleep(4)