# BITP3123_GroupProject

LIM WEI JIE B03220102

--------------------------------------------------------------------
YSL HOTEL ROOM BOOKING 
--------------------------------------------------------------------

1.  How many apps involved
-  Admin (Hotel Staff)
-  User (Hotel Customer)

---------------------------------------------------------------------

2. Explanantion of each apps
-  Admin
  
    i) Manage Bookings
   
   ii) Manage Rooms

   - Admin can see the user book which room and how long they are staying.
   - Admin can edit the room

-  User
  
    i) Sign-up
   
   ii) log-in
   
  iii) view room and book room

   iv) view booking

   - user need to sign up and then log in to the YSL Hotel page
   - User can view the room and then book selected room
   - User also can view the booking that they had done

---------------------------------------------------------------------

3.  Architecture/Layer diagram for each of the apps

   -

---------------------------------------------------------------------

4.  List of URL end points middleware RESTful

- http://localhost/dad_lim/cancel_booking.php
- http://localhost/dad_lim/login.php
- http://localhost/dad_lim/signup.php
- http://localhost/dad_lim/fetch_booking.php?username=" + userName
- http://localhost/dad_lim/insert_booking.php
- http://localhost/dad_lim/check_bookings.php


----------------------------------------------------------------------

5.  The database and tables involve in the projects
   i) booking

      - id, room_id, room_type, total_price, check_in_date, check_out_date, username

  ii) user  
       - id, username, password, email, phone_number

