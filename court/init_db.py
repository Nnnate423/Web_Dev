from book.models import *
from django.utils import timezone
import datetime

c_list=[]
c_list.append(court(courtname = "Marine Terrace", location = "6 Marine Terrace, Singapore 440006", indoor = False, std = True ))
#c_list.append(court(courtname = "", location = "", indoor = False, std = True ))
c_list.append(court(courtname = "Net free Courts", location = "5 Marsiling Dr, Singapore 730005", indoor = False, std = True ))
c_list.append(court(courtname = "SRC Courts", location = "61 Nanyang Dr, Singapore 637335", indoor = False, std = True ))
c_list.append(court(courtname = "St George's Courts", location = "4C St George's Ln, Singapore 322004", indoor = True, std = False ))
c_list.append(court(courtname = "Turf City Courts", location = "220 Turf Club Rd, Singapore 287994", indoor = False, std = False ))
for c in c_list: c.save()

for c in c_list: 
    s=slot(courtid = c, day = datetime.date.today())
    s.save()
