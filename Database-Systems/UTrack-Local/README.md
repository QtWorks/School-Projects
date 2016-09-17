Example Admin Account
login: 'dark_knight'
password: 'ih8joker'

New users can also be admin if they know the "admin password"
Admin password: 'databases12'

1. [5pts] Registration: Registration: a new user has to provide the appropriate information; he/she can pick a login-name and a password. The login name should be checked for uniqueness.

	- On startup, select:
	2
	Enter info
	
2. [5pts] Visit: After registration, a user can record a visit to any POI (the same user may visit the samePOI multiple times). Each user session (meaning each time after a user has logged into the system) may add one or more visits, and all visits added by a user in a user session are reported to him/her for the final review and confirmation, before they are added into the database.

	- Various ways of seeing lists of POIs (search, visited by user, etc.)
	- Select POI from list
	- Select "add visit and review" option
	
3. [5pts] New POI: The admin user records the details of a new POI.

	- Admin logs in, Selects option to Add new POI
	- User does not have this capability
	- In order for the POI to be included on searches, the admin is asked to record the details of a visit and their feedback

4. [5pts] Update POI: The admin user may update the information regarding an existing POI.

	- Various ways of seeing lists of POIs (search, visited by user, etc.)
	- Select POI from list
	- Select "update POI info" option
	- Users will not be allowed to proceed, but Admins will

5. [5pts] Favorite recordings: Users can declare a POI as his/her favoriate place to visit.

	- Various ways of seeing lists of POIs (search, visited by user, etc.)
	- Select POI from list
	- Select "declare as your favorite" option

6. [5pts] Feedback recordings: Users can record their feedback for a POI. We should record the date, the numerical score (0= terrible, 10= excellent), and an optional short text. No changes are allowed; only one feedback per user per POI is allowed.

	- Feedbacks are recorded along with visits
	- A user can record as many visits as they desire, but only one feedback per POI
	- This is enforced in the code. A check is made to see if a user has already left a feedback for the POI, and the option for leaving another is never given

7. [5pts] Usefulness ratings: Users can assess a feedback record, giving it a numerical score 0, 1, or 2 ('useless',
'useful', 'very useful' respectively). A user should not be allowed to provide a usefulness-rating for his/her
own feedbacks.

	- A user can see all feedbacks associated with a selected POI
	- A user can rate the usefulness of a review/feedback by selecting the review and marking it with 1, 2 or 3 (stored as 0, 1 and 2 in database)

8. [5pts] Trust recordings: A user may declare zero or more other users as 'trusted' or 'not-trusted'.

	- A user can view other users that have visited a selected POI
	- After selecting the user, they can mark as trusted/not trusted
	- A user can only mark another user once

9. [20pts] POI Browsing: Users may search for POIs, by asking conjunctive queries on the price (a range),
and/or address (at CITY or State level), and/or name by keywords, and/or category. Your system should
allow the user to specify that the results are to be sorted (a) by price, or (b) by the average numerical score
of the feedbacks, or (c) by the average numerical score of the trusted user feedbacks.

	- Help Menu for Search (included in program)
		- Places of Interest have many options of search criteria
		- To search by price range:
		- low-high (no whitespace, only numbers)
		- Search by Country, State, City
		- Search By Category
		- Search By Keyword
		- *Multiple keywords can be included in search.
		- SEPARATED BY COMMAS
		- Place of Interest must have ALL keywords entered into search
	- When searching with keywords, POIs that have ALL keywords are returned

10. [5pts] Useful feedbacks: For a given POI, a user could ask for the top n most 'useful' feedbacks. The
value of n is user-specified (say, 5, or 10). The 'usefulness' of a feedback is its average 'usefulness' score.

	- Various ways of arriving at location
	- Select option to view top 'n' most useful reviews 

11. [10pts] Visiting suggestions: Like most e-commerce websites, when a user records his/her visit to a POI
'A', your system should give a list of other suggested POIs. POI 'B' is suggested, if there exist a user 'X'
that visited both 'A' and 'B'. The suggested POIs should be sorted on decreasing total visit count (i.e., most
popular first); count only visits by users like 'X'.

	- Any time a visit is recorded, immediately after feedback is given (if it doesn't already exist), a user is provided with POIs that others have visited that have also visited the location (does NOT necessarily include the locations that the current user has visited)

12. [10pts] 'Two degrees of separation': Given two user names (logins), determine their 'degree of separation',
defined as follows: Two users 'A' and 'B' are 1-degree away if they have both favorited at least one common
POI; they are 2-degrees away if there exists an user 'C' who is 1-degree away from each of 'A' and 'B', AND
'A' and 'B' are not 1-degree away at the same time.

	- Various ways of selecting users
	- Select user
	- Select option to find degree of separation with other user
	- Also, from Admin menu, admin can select "Degree of separation finder" and input two users
	- The result will show the degree of separation of the two users
	- Can also select option to view all users 'n' degrees away from user
	- User will select number of degrees to show
	- The result will show all of those users 'n' degrees away from selected user
	- *Bonus* - Shows more than just two degrees of separation. Shows ANY degree of separation 
	
13. [10pts] Statistics: At any point, a user may want to show the list of the m (say m = 5) most popular POIs (in terms of total visits) for each category, the list of m most expensive POIs (defined by the average cost per head of all visits to a POI) for each category, the list of m highly rated POIs (defined by the average scores from all feedbacks a POI has received) for each category

	- From base user menu (user can enter 'B' to go back from any menu), a user can select any of these options
	- User is asked for n, and POIs are shown

14. [5pts] User awards: At random points of time, the admin user wants to give awards to the 'best' users;
thus, the admin user needs to know the top m most 'trusted' users (the trust score of a user is the count of users 'trusting' him/her, minus the count of users 'not-trusting' him/her), the top m most 'useful' users (the usefulness score of a user is the average 'usefulness' of all of his/her feedbacks combined)

	- This option is shown at the Admin base menu (user/admin can enter 'B' to go back from any menu) under the "Statistics for Awards" option
	- Admin can select which report he would like to generate
	- Admin is asked for size of n, and users are shown
	