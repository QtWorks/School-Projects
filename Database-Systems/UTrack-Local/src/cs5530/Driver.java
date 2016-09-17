package cs5530;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import model.*;
import service.*;
import exceptions.*;
import static model.Columns.*;

public class Driver {
	
	private static final int ATTEMPTS_ALLOWED = 3;
	private static final int MINIMUM_LOGIN_LENGTH = 4;
	static BufferedReader in;
	static AddressService addressService;
	static FeedbackRatingService ratingService;
	static FeedbackService feedbackService;
	static KeywordService keywordService;
	static POIService poiService;
	static ShortTextService textService;
	static TrustedService trustedService;
	static UserService userService;
	static VisitService visitService;
	static FavoriteService favoriteService;
	static UserRecord currentUser;
	
	public static void startupMenu() throws Exception{
        in = new BufferedReader(new InputStreamReader(System.in));
        
        Connector con = new Connector();
        
        addressService = new AddressService(con);
        ratingService = new FeedbackRatingService(con);
        feedbackService = new FeedbackService(con);
        keywordService = new KeywordService(con);
        poiService = new POIService(con);
        textService = new ShortTextService(con);
        trustedService = new TrustedService(con);
        userService = new UserService(con);
        visitService = new VisitService(con);
        favoriteService = new FavoriteService(con);
        		
        print("Welcome to UTrack",
					"Please select from the following options",
					"1. Login",
					"2. Register as a new user",
					"3. Quit");
		int option = getInt(in.readLine(),1,3);
		if(option == 1){
			login();
		}
		else if(option == 2){
			registerNewUser(con);
			beginSession();
		}
		else if(option == 3){
			throw new EarlyTerminationException("Ending program");
		}
	}
	/**
	 * Logs a user in with three attempts for both the login name and the password
	 * @param in
	 * @param con 
	 * @throws IOException
	 * @throws EarlyTerminationException
	 */
	private static void login() throws IOException, EarlyTerminationException {
		String login = "";		
		boolean validPassword = false;
		int attempts = 0;
		while(!validPassword){
			print("Please enter your login");
			login = in.readLine();
			print("Please enter your password");
			String password = in.readLine();
			currentUser = userService.getUserByLogin(login);
			if(currentUser == null){
				print("There is no user with the login '" + login + "'","Please try again.");
				continue;
			}
			validPassword = currentUser != null && currentUser.getPassword().equals(password);
			attempts++;
			if (attempts > ATTEMPTS_ALLOWED){
				throw new EarlyTerminationException("Too many incorrect attempts. Shutting down");
			}else if(!validPassword)
				print("Invalid combination. Please try again");
		}
		if(login.length() > 0){
			beginSession();
		}
	}

	private static void beginSession() throws IOException, EarlyTerminationException {
		print("Welcome " + currentUser.getName());
		if(currentUser.isAdmin()){
			adminSession();
		}else{
			userSession();
		}
	}
	private static void userSession() throws IOException, EarlyTerminationException {
		String input = "";
		while(!input.equalsIgnoreCase("L") && !input.equalsIgnoreCase("Q")){
			print("Choose one of the following options:",
					"1. Search points of interest by location, category or keyword",
					"2. View places you have visited",
					"3. View top 'n' most popular Points of Interest for each category",
					"4. View top 'n' most expensive Points of Interest for each category",
					"5. View top 'n' highest rated Points of Interest for each category",
					"L - Logout",
					"Q - Quit");
			input = in.readLine();
			if(input.equalsIgnoreCase("L")){
				currentUser = null;
				login();
			}else if(input.equalsIgnoreCase("Q")){
				throw new EarlyTerminationException("Ending program");
			}else{
				int choice = getInt(input,1,6);
				if(choice == 1){
					searchPOI();
				}else if(choice == 2){
					POIbyUser(currentUser.getLogin());
				}else if(choice == 3){
					print("How many would you like to see per category?");
					mostPopularPOIByCategory(getInt(in.readLine(),1,1000));
				}else if(choice == 4){
					print("How many would you like to see per category?");
					mostExpensivePOIByCategory(getInt(in.readLine(),1,1000));
				}else if(choice == 5){
					print("How many would you like to see per category?");
					highestRatedPOIByCategory(getInt(in.readLine(),1,1000));
				}
			}
		}
	}
	private static void adminSession() throws IOException, EarlyTerminationException {
		String input = "";
		while(!input.equalsIgnoreCase("L") && !input.equalsIgnoreCase("Q")){
			print("Choose one of the following options:",
					"ADMIN",
					"1. Add a new Point of Interest",
					"2. View/Edit a Point of Interest",
					"3. View all users",
					"4. Statistics for Awards",
					"5. Degree of Separation Finder",
					"USER",
					"6. Search points of interest by location, category or keyword",
					"7. View places you have visited",
					"8. View top 'n' most popular Points of Interest for each category",
					"9. View top 'n' most expensive Points of Interest for each category",
					"10. View top 'n' highest rated Points of Interest for each category",
					"L - Logout",
					"Q - Quit");
			input = in.readLine();
			if(input.equalsIgnoreCase("L")){
				currentUser = null;
				login();
			}else if(input.equalsIgnoreCase("Q")){
				throw new EarlyTerminationException("Ending program");
			}else{
				int choice = getInt(input, 0,8);
				if(choice == 1){
					addPOI(null);
				}else if(choice == 2){
					poiSelectionMenu(poiService.getAllPois());
				}else if(choice == 3){
					userSelectionMenu(userService.getAllUsers());
				}else if(choice == 4){
					awardStats();
				}else if(choice == 5){
					getTwoUserDegreeOfSeparation();
				}
				else if(choice == 6){
					searchPOI();
				}else if(choice == 7){
					POIbyUser(currentUser.getLogin());
				}else if(choice == 8){
					print("How many would you like to see per category?");
					mostPopularPOIByCategory(getInt(in.readLine(),1,1000));
				}else if(choice == 9){
					print("How many would you like to see per category?");
					mostExpensivePOIByCategory(getInt(in.readLine(),1,1000));
				}else if(choice == 10){
					print("How many would you like to see per category?");
					highestRatedPOIByCategory(getInt(in.readLine(),1,1000));
				}
			}
		}
	}
	private static void getTwoUserDegreeOfSeparation() throws IOException {
		print("Enter the first user:");
		String u1 = in.readLine();
		UserRecord user1 = userService.getUserByLogin(u1);
		if(user1 == null){
			print("No such user: " + u1);
			return;
		}
		print("Enter the second user:");
		String u2 = in.readLine();
		UserRecord user2 = userService.getUserByLogin(u2);
		if(user2 == null){
			print("No such user: " + u2);
			return;
		}
		int degrees = findDegreeOfSeparation(user1, user2);
		if(degrees < 0)
			print("There is no connection between " + u1 + " and " + u2);
		else if (degrees == 1)
			print("There is 1 degree of separation between " + u1 + " and " + u2);
		else
			print("There are " + degrees + " degrees of separation between " + u1 + " and " + u2);
		
	}
	private static void mostPopularPOIByCategory(int n) throws IOException {
		ArrayList<ArrayList<POIRecord>> popular = poiService.getTopByCategory(n, 1);
		poiSelectionByCategory(popular);		
	}
	private static void mostExpensivePOIByCategory(int n) throws IOException {
		ArrayList<ArrayList<POIRecord>> expensive = poiService.getTopByCategory(n, 2);
		poiSelectionByCategory(expensive);
	}
	private static void highestRatedPOIByCategory(int n) throws IOException {
		ArrayList<ArrayList<POIRecord>> best = poiService.getTopByCategory(n, 3);
		poiSelectionByCategory(best);
	}
	private static void poiSelectionByCategory(ArrayList<ArrayList<POIRecord>> categories) throws IOException{
		String input = "";
		while(true){
			ArrayList<POIRecord> allResults = new ArrayList<POIRecord>();
			int i = 0;
			for(ArrayList<POIRecord> category : categories){
				print(category.get(0).getCategory().toUpperCase());
				for(POIRecord poi : category){
					print((i+1) + ".\t" + poi.getName());
					allResults.add(poi);
					i++;
				}
			}
			print("B - Back");
			input = in.readLine();
			if(input.equalsIgnoreCase("B"))
				return;
			if(allResults.size() > 0)
				print("Select Place of Interest by its number");
			else{
				print("There are no Places of Interest");
				return;
			}
			int selection = getInt(in.readLine(),1,allResults.size());
			print("Here is the full info of the Place of Interest", allResults.get(selection-1).toStringFull());
			poiMenu(allResults.get(selection-1));
		}
	}
	private static void awardStats() throws IOException {
		while(true){
			print("Select which statistics you would like to see",
					"1. Top 'n' most trusted users",
					"2. Top 'n' most useful users",
					"B - Back");
			String input = in.readLine();
			if(input.equalsIgnoreCase("B"))
				return;
			int choice = getInt(input,1,2);
			
			print("How many users would you like to see?");
			int n = getInt(in.readLine(),1,10000);
			ArrayList<UserRecord> top = userService.getTopUsers(n,choice);
			userSelectionMenu(top);	
		}
			
	}
	private static int getInt(String line, int lowerBound, int upperBound) throws NumberFormatException, IOException {
		int answer = getInt(line);
		while(answer < lowerBound || answer > upperBound){
			print("Not an available option. Please try again");
			answer = getInt(in.readLine());
		}		
		return answer;
	}
	private static int getInt(String line) throws IOException{
		try{
			int answer = Integer.parseInt(line);
			return answer;
		}catch(NumberFormatException e){
			print("Not a number. Please try again");
			return getInt(in.readLine());
		}
	}
	private static double getDouble(String line) throws IOException{
		try{
			double answer = Double.parseDouble(line);
			return answer;
		}catch(NumberFormatException e){
			print("Not a number. Please try again");
			return getDouble(in.readLine());
		}
	}
	private static void addPOI(POIRecord previous) throws IOException {
		if(previous == null){
			POIRecord pr = new POIRecord();
			print("Please enter the following attributes for the Point of Interest");
			print("Name:");
			pr.setName(in.readLine());
			AddressRecord ar = getAddress();
			pr.setAddress(ar);
			print("URL:");
			pr.setUrl(in.readLine());
			print("Phone number:");
			pr.setPhone(in.readLine());
			print("Year established:");
			pr.setYear_est(getInt(in.readLine()));
			print("Hours:");
			pr.setHours(in.readLine());
			print("Price:");
			pr.setPrice(getDouble(in.readLine()));
			print("Category:");
			pr.setCategory(in.readLine());
			print("Keywords: (Example: Language1/Keyword1, Language2/Keyword2, etc.)");
			pr.setKeywords(getKeywords(in.readLine()));
			if(confirmPOI(pr))
				poiService.insertPOI(pr);
			
			ArrayList<VisitRecord> visits = visitService.getVisitRecords(currentUser,pr);
			if(visits.size() > 0){
				print("Would you like to record another visit? (Y or N)");
				String answer = in.readLine();
				if(answer.equalsIgnoreCase("Y")){
					getVisit(pr);
				}
				ArrayList<FeedbackRecord> feedbacks = feedbackService.getFeedbackRecords(currentUser, pr);
				if(feedbacks.size() > 0){
					print("You have already given feedback for this location");
				}
				else{
					getFeedback(visits.get(0));
				}
			}else{
				getVisit(pr);
			}
		}
		else{
			print("Please enter the following attributes for the Point of Interest");
			print("Name: (Currently " + previous.getName() + ")");
			previous.setName(in.readLine());
			AddressRecord ar = getAddress();
			previous.setAddress(ar);
			previous.setAddressId(ar.getId());
			print("URL: (Currently " + previous.getUrl() + ")");
			previous.setUrl(in.readLine());
			print("Phone number: (Currently " + previous.getPhone() + ")");
			previous.setPhone(in.readLine());
			print("Year established: (Currently " + previous.getYear_est() + ")");
			previous.setYear_est(getInt(in.readLine()));
			print("Hours: (Currently " + previous.getHours() + ")");
			previous.setHours(in.readLine());
			print("Price: (Currently " + previous.getPrice() + ")");
			previous.setPrice(getDouble(in.readLine()));
			print("Category: (Currently " + previous.getCategory() + ")");
			previous.setCategory(in.readLine());
			print("Keywords: (Example: Language1/Keyword1, Language2/Keyword2, etc. Currently " + previous.getKeywordsString() + ")");
			keywordService.removeAllKeywords(previous.getId());
			previous.setKeywords(getKeywords(in.readLine()));
			if(confirmPOI(previous))
				poiService.updatePOI(previous);

		}		
	}
	private static ArrayList<KeywordRecord> getKeywords(String line) {
		String[] records = line.split(",");
		ArrayList<KeywordRecord> keywordList = new ArrayList<KeywordRecord>();
		for(int i = 0; i < records.length; i++){
			String r = records[i].trim();
			String[] split = r.split("/");
			KeywordRecord record = new KeywordRecord(split[0].trim(),split[1].trim());
			keywordList.add(record);
		}
		return keywordList;
	}
	private static boolean confirmPOI(POIRecord poi) throws IOException {
		print("Please confirm the following information about your POI (Y or N)", poi.toStringFull());
		String answer = in.readLine();
		if(answer.equalsIgnoreCase("Y"))
			return true;
		else
			print(" will not be recorded");
		return false;
	}
	private static void getVisit(POIRecord pr) throws IOException {
		VisitRecord vr = new VisitRecord();
		vr.setUser(currentUser);
		vr.setPoi(pr);
		print("Enter the date of your visit");
		java.sql.Date date = getDate(in.readLine());
		if(date != null)
			vr.setDate(date);
		else
			print("Invalid date");
		print("How much did you spend?");
		vr.setSpent(getDouble(in.readLine()));
		print("How many were in your party?");
		vr.setParty(getInt(in.readLine()));
		if(confirmVisit(vr)){
			visitService.insertVisit(vr);
			if(!feedbackExists(vr))
				getFeedback(vr);
			else
				print("You have already given feedback for this location");
			ArrayList<POIRecord> suggested = poiService.getSuggestedPOIs(vr);
			if(suggested.size() > 0){
				print("Users that visited " + pr.getName() + " also visited:");
				poiSelectionMenu(suggested);
			}else{
				print("No suggestions for " + vr.getPoi().getName(),"It must be a fairly new place!");
			}
		}
	}
	private static boolean feedbackExists(VisitRecord vr) {
		return feedbackService.getFeedbackRecords(vr.getUser(), vr.getPoi()).size() > 0;
	}
	private static boolean confirmVisit(VisitRecord vr) throws IOException {
		print("Please confirm the following information about your visit (Y or N)",vr.toStringFull());
		String answer = in.readLine();
		if(answer.equalsIgnoreCase("Y"))
			return true;
		else
			print("Visit will not be recorded");
		return false;
	}
	private static void getFeedback(VisitRecord vr) throws NumberFormatException, IOException {
		FeedbackRecord fr = new FeedbackRecord();
		fr.setUser(vr.getUser());
		fr.setPoi(vr.getPoi());
		print("How would you rate your experience on a scale from 1-10?");
		int score = getInt(in.readLine(),1,10);
		fr.setScore(score);
		feedbackService.insertFeedback(fr);
		getText(vr);
	}
	private static void getText(VisitRecord vr) throws IOException {
		ShortTextRecord text = new ShortTextRecord();
		text.setVisit(vr);
		print("Would you like to add any additional information? (Y or N)");
		String answer = in.readLine();
		if(answer.equalsIgnoreCase("y")){
			print("Enter any additional information (140 characters or less)");
			text.setText(in.readLine());
			textService.insertText(text);
		}else if(answer.equalsIgnoreCase("n")){
			print("Thanks for your review of " + vr.getPoi().getName());
		}else{
			print("Not a valid response");
		}
	}
	private static java.sql.Date getDate(String sdate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			java.sql.Date date = new java.sql.Date(format.parse(sdate).getTime());
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	private static void POIbyUser(String user) throws NumberFormatException, IOException {
		ArrayList<POIRecord> poiList = poiService.getPOIbyUser(user);
		poiSelectionMenu(poiList);
		
		
	}
	private static void poiMenu(POIRecord selection) throws IOException {
		print("Select one of the following options:",
				"1. View list of all reviews",
				"2. Record a visit and review",
				"3. Declare as your favorite",
				"4. Update POI info",
				"5. View users that have been here",
				"6. Top 'n' most useful feedbacks",
				"B - Back");
		String input = in.readLine();
		if(input.equalsIgnoreCase("B"))
			return;
		int choice = getInt(input,1,6);
		if(choice == 1){
			feedbacksByPOI(selection);
		}else if(choice == 2){
			getVisit(selection);
		}else if(choice == 3){
			favoriteService.insertFavorite(currentUser, selection);
		}else if(choice == 4){
			if(currentUser.isAdmin())
				addPOI(selection);
			else
				print("You do not have admin status");
		}else if(choice == 5){
			usersByPoi(selection);
		}else if(choice == 6){
			print("How many feedbacks would you like to see?");
			int n = getInt(in.readLine(),0,10000);
			topFeedbacksBYPOI(selection, n);
		}
		else{
			print("Invalid choice");
		}
	}
	private static void poiSelectionMenu(ArrayList<POIRecord> poiList) throws NumberFormatException, IOException {
		String input = "";
		while(true){
			for(int i = 0; i < poiList.size(); i++){
				POIRecord poi = poiList.get(i);
				print((i+1) + ".\t" + poi.getName());
			}
			print("B - Back");
			input = in.readLine();
			if(input.equalsIgnoreCase("B"))
				return;
			if(poiList.size() > 0)
				print("Select Place of Interest by its number");
			else{
				print("There are no Places of Interest");
				return;
			}
			int selection = getInt(input,1,poiList.size());
			print("Here is the full info of the Place of Interest", poiList.get(selection-1).toStringFull());

			poiMenu(poiList.get(selection-1));
		}
		
	}
	
	private static void usersByPoi(POIRecord poi) throws IOException {
		userSelectionMenu(userService.getUsersByPOI(poi));
		
	}
	private static void userMenu(UserRecord user) throws IOException {
		while(true){
			print("Select one of the following options:",
					"1. View list of all reviews from " + user.getLogin(),
					"2. Mark " + user.getLogin() + " as trusted/not-trusted",
					"3. Find degree of separation with " + user.getLogin(),
					"4. Find all users 'n' degrees away from " + user.getLogin(),
					"B - Back");
			String input = in.readLine();
			if(input.equalsIgnoreCase("B"))
				return;
			int choice = getInt(input,1,4);
			if(choice == 1){
				feedbacksByUser(user);
			}else if(choice == 2){
				boolean validChoice = false;
				while(!validChoice){
					print("Do you trust this user? (Y or N)");
					String answer = in.readLine();
					if(answer.equalsIgnoreCase("Y")){
						trustedService.markUserTrustedBy(user,currentUser,true);
						validChoice = true;
					}else if(answer.equalsIgnoreCase("N")){
						trustedService.markUserTrustedBy(user,currentUser,false);
						validChoice = true;
					}else{
						print("Invalid choice");
					}
				}
				
			}else if(choice == 3){
				if(currentUser.equals(user))
					print("That's YOU!!");
				else{
					int degrees = findDegreeOfSeparation(currentUser, user);
					if(degrees < 0)
						print("There is no connection between you and " + user.getLogin());
					else if (degrees == 1)
						print("There is 1 degree of separation between you and " + user.getLogin());
					else
						print("There are " + degrees + " degrees of separation between you and " + user.getLogin());
				}
			}else if(choice == 4){
				print("How many degrees of distance would you like to see?");
				int n = getInt(in.readLine(),1,1000);
				ArrayList<UserRecord> users = getNDegreesAway(user,n);
				if(users.isEmpty())
					print("No users " + n + " degrees away from " + user.getLogin());
				else
					userSelectionMenu(users);
			}
			else{
				print("Invalid choice");
			}
		}
	}
	private static ArrayList<UserRecord> getNDegreesAway(UserRecord user, int n) {
		ArrayList<UserRecord> result = new ArrayList<UserRecord>();
		Hashtable<String,Integer> distances = getDistances(user);
		Enumeration<String> e = distances.keys();
	    while (e.hasMoreElements()){
	    	String login = e.nextElement();
	    	if(distances.get(login) == n){
	    		result.add(userService.getUserByLogin(login));
	    	}
	    }
		return result;
	}
	/**
	 * Gets degree distance from user to all other users
	 * (Could definitely be a little more efficient, but it works)
	 * @param user
	 * @return
	 */
	private static Hashtable<String, Integer> getDistances(UserRecord user) {
		Hashtable<String,Integer> distance = new Hashtable<String,Integer>();
		distance.put(user.getLogin(), 0);
		HashSet<String> visited = new HashSet<String>();
		Queue<UserRecord> q = new LinkedList<UserRecord>();
		q.add(user);
		while(!q.isEmpty()){
			UserRecord current = q.remove();
			visited.add(current.getLogin());
			ArrayList<UserRecord> children = userService.getUsersOneDegreeAway(current);
			for(UserRecord child : children){
				if(distance.containsKey(child.getLogin())){
					int prev = distance.get(child.getLogin());
					if(distance.get(current.getLogin()) + 1 < prev)
						distance.replace(child.getLogin(), distance.get(current.getLogin()) + 1);
				}else{
					distance.put(child.getLogin(), distance.get(current.getLogin()) + 1);
				}
				if(!visited.contains(child.getLogin()))
					q.add(child);
			}
		}
		return distance;
	}
	/**
	 * Returns the degrees of separation between two users
	 * Returns -1 if there is no connection between the two
	 * @param user
	 * @param searchingFor
	 * @return
	 */
	private static int findDegreeOfSeparation(UserRecord user, UserRecord searchingFor) {
		Hashtable<String,Integer> distance = getDistances(user);
		if(distance.containsKey(searchingFor.getLogin())){
			return distance.get(searchingFor.getLogin());
		}
		else{
			return -1;
		}
	}
	private static void feedbacksByUser(UserRecord user) throws IOException {
		ArrayList<FeedbackRecord> feedbacks = feedbackService.getFeedbackByUser(user);
		feedbackSelectionMenu(feedbacks);
	}
	
	private static void feedbacksByPOI(POIRecord poi) throws IOException{
		ArrayList<FeedbackRecord> feedbacks = feedbackService.getFeedbacksByPOI(poi);
		feedbackSelectionMenu(feedbacks);
	}
	
	private static void topFeedbacksBYPOI(POIRecord poi, int n) throws IOException {
		ArrayList<FeedbackRecord> feedbacks = feedbackService.getTopFeedbacksByPOI(poi,n);
		feedbackSelectionMenu(feedbacks);
	}
	private static void feedbackMenu(FeedbackRecord feedback) throws IOException {
		if(feedback.getUser().getLogin().equals(currentUser.getLogin())){
			print("This is your own review. There is nothing you can do with it");
			return;
		}
		print("How useful was this review?",
				"1. Useless",
				"2. Useful",
				"3. Very Useful",
				"B - Back");
		try {
			String input = in.readLine();
			if(input.equalsIgnoreCase("B"))
				return;
			int rating = getInt(input,1,3);
			FeedbackRatingRecord fr = new FeedbackRatingRecord();
			fr.setMarkingUser(currentUser);
			fr.setFeedback(feedback);
			fr.setRating(rating - 1);
			ratingService.insertFeedbackRating(fr);
		} catch (NumberFormatException e) {
			print("Invalid input");
		}
	}
	private static void feedbackSelectionMenu(ArrayList<FeedbackRecord> feedbacks) throws IOException{
		String input = "";
		while(true){
			for(int i = 0; i < feedbacks.size(); i++){
				FeedbackRecord feedback = feedbacks.get(i);
				print((i+1) + ". " + feedback.toString());
			}
			print("B - Back");
			input = in.readLine();
			if(input.equalsIgnoreCase("B"))
				return;
			if(feedbacks.size() > 0)
				print("Select review by its number");
			else{
				print("There are no reviews");
				return;
			}
			int selection = getInt(input,1,feedbacks.size());
			feedbackMenu(feedbacks.get(selection-1));
		}
		
		
	}
	private static void userSelectionMenu(ArrayList<UserRecord> userList) throws IOException {
		String input = "";
		while(true){
			for(int i = 0; i < userList.size(); i++){
				UserRecord user = userList.get(i);
				print((i+1) + ".\t" + user.getLogin());
			}
			print("B - Back");
			input = in.readLine();
			if(input.equalsIgnoreCase("B"))
				return;
			print("Select User by their number");
			int selection = getInt(input,1,userList.size());
			userMenu(userList.get(selection-1));
		}
		
	}

	private static void searchPOI() throws IOException {
		showSearchHelp();
		SearchModel search = new SearchModel();
		print("Price Range: (low-high)");
		search.setPriceRange(in.readLine().trim());
		print("Country:");
		search.setCountry(in.readLine().trim());
		print("State:");
		search.setState(in.readLine().trim());
		print("City:");
		search.setCity(in.readLine().trim());
		print("Keywords: (Separated by comma)");
		search.setKeywords(in.readLine().trim());
		print("Category:");
		search.setCategory(in.readLine().trim());
		print("Order by:",
				"1. Price",
				"2. Average Feedback Score",
				"3. Average Feedback Score of trusted user feedbacks",
				"4. No order");
		int sortedBy = getInt(in.readLine(),1,4);
		search.setSortedBy(sortedBy);
		poiSelectionMenu(poiService.getSearchResults(search));
		
	}
	
	private static void showSearchHelp() {
		print("Places of Interest have many options of search criteria",
				"To search by price range:",
				"low-high (no whitespace, only numbers)",
				"Search by Country, State, City",
				"Search By Category",
				"Search By Keyword",
				"*Multiple keywords can be included in search.",
				"SEPARATED BY COMMAS",
				"Place of Interest must have ALL keywords entered into search"
				);
	}
	private static void registerNewUser(Connector con) throws IOException{
		UserRecord ur = new UserRecord();		
		setLogin(ur);		
		setPassword(ur);		
		setName(ur);		
		setAddress(ur);		
		setPhone(ur);
		setAdmin(ur);
		userService.insertUser(ur);		
		currentUser = ur;
	}
	
	private static void setLogin(UserRecord ur) throws IOException {
		boolean availableLogin = false;
		
		while(!availableLogin){
			print("Please enter your login. Must be at least " + MINIMUM_LOGIN_LENGTH + " characters");
			String login = in.readLine();
			if(login.length() < MINIMUM_LOGIN_LENGTH){
				continue;
			}
			availableLogin = userService.isAvailableLogin(login);
			if(!availableLogin)
				print("Login is already taken. Please try again");
			else
				ur.setLogin(login);
		}
		
	}
	private static void setPassword(UserRecord ur) throws IOException {
	
		print("Please enter your password");
		String password = in.readLine();
		
		print("Please confirm your password");
		while(!in.readLine().equals(password)){
			print("Incorrect. Please try again");
		}
		ur.setPassword(password);
		
		
	}
	private static void setName(UserRecord ur) throws IOException {
		print("Please enter your full name");
		ur.setName(in.readLine());
		
	}
	private static void setAdmin(UserRecord ur) throws IOException {

		print("Are you an Admin? (Y or N)");
		String answer = in.readLine();
		if(answer.equalsIgnoreCase("Y")){
			print("Please enter password:");
			String attempt = in.readLine();
			while(!attempt.equals(ADMIN_PASSWORD)){
				print("Incorrect password. Please try again");
				attempt = in.readLine();
			}
			ur.setAdmin(true);				
		}else if(answer.equalsIgnoreCase("N")){
			ur.setAdmin(false);
		}else{
			
		}
	}
	private static void setAddress(UserRecord ur) throws IOException {
		print("Please enter your address");
		AddressRecord userAddress = getAddress();		
		ur.setAddress(userAddress);
	}
	private static void setPhone(UserRecord ur) {
		print("Please enter your phone number");
		try{
			ur.setPhone(in.readLine());
		}catch(NumberFormatException e){
			print("Phone number is in wrong format");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	private static AddressRecord getAddress() throws IOException {
		AddressRecord ar = new AddressRecord();
		print("Country:");		
		ar.setCountry(in.readLine());
		print("State:");
		ar.setState(in.readLine());
		print("City:");
		ar.setCity(in.readLine());
		print("Zip Code:");
		ar.setZip(getInt(in.readLine()));
		print("Street:");
		ar.setStreet(in.readLine());		
		print("Number");
		ar.setNumber(getInt(in.readLine()));
		
		addressService.insertAddress(ar);
		return ar;
	}
	/**
	 * Prints lines. Optional parameters
	 * @param lines
	 */
	public static void print(String ...lines){
		System.out.println("");
		for(String line : lines){
			System.out.println(line);
		}
	}

	public static void main(String[] args) {
		try {
			startupMenu();
		} 
		catch(EarlyTerminationException e){
			print(e.getMessage());
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

