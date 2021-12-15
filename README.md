# Report
This is a report containing all the achieved functionalities as written in the requirements pdf along with the used design patterns (Factory, Singleton). Each section contains the code snippets with the description of each feature, and what was done in order to achieve that.
## Connecting and managing databases
- To connect to already existing schemas, We've created a file that holds all the connection profiles' information and is read using the "readProfile()" method that splits the (id,username,password,port number,database name) respectively using commas (the id was used to index the connection's along with their names). We've also logged the info for reading or accessing the file to be later saved to the "logs.txt" file.
```ruby
public static ConnectionInfo readProfile(String id){
        String username = null;
        String password = null;
        String port = null;
        String dbName = null;
        File inputFile = new File("./Profiles.txt");
        try{
            Scanner in = new Scanner(inputFile);
            while(in.hasNextLine()){
                String string = in.nextLine();
                String[] stringArray = string.split(",");
                if(id.equals(stringArray[0])) {
                    username = stringArray[1];
                    password = stringArray[2];
                    port = stringArray[3];
                    dbName = stringArray[4];
                }
            }
            Logger.logInfo("Successfully read profile");
            return new ConnectionInfo(username,password,port,dbName);
        }
        catch (FileNotFoundException ex){
            Logger.logError("Failed to read profile");
            System.out.println("FileNotFoundException: " + ex.getMessage());
            return null;
        }
    }
```
- The "ShowProfiles()" method only outputs all the connections in "Profiles.txt" with their index in "1- database name" format. We've also used the logger to represent the success of reading the profiling text file.
```ruby
public static void showProfiles(){
        File inputFile = new File("./Profiles.txt");
        try {
            Scanner in = new Scanner(inputFile);
            for(int i = 0;in.hasNextLine();i++){
                String string = in.nextLine();
                String[] stringArray = string.split(",");
                System.out.println((i+1) + "- " + stringArray[4]);
            }
            Logger.logInfo("Successfully read profiles");
        }
        catch (FileNotFoundException ex){
            Logger.logError("Failed to read profiles");
            System.out.println("FileNotFoundException: " + ex.getMessage());
        }
```
- We've also added a "createProfile()" method for admins to add new database connections to connect to. It uses the same text file used in the past 2 previous method to input the newly user-inputted info to the file.
```ruby
public static void createProfile(String info){
        File inputFile = new File("./Profiles.txt");
        String[] stringArray = null;
        String index = null;
        try {
            Scanner in = new Scanner(inputFile);
            while (in.hasNextLine()) {
                String string = in.nextLine();
                stringArray = string.split(",");
            }
            index = stringArray[0];
            int i = Integer.parseInt(index);
            i +=1;
            index = Integer.toString(i);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./Profiles.txt",true));
            writer.append("\n");
            writer.append(index + "," + info);
            writer.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
```
- Since changing the connection profile at runtime is a requirement. We've added an "editProfile()" method that could be selected and executed and runtime by the user. It checks the "Profiles.txt" for the id-sent parameter and changes the connection info at that line to the user-specified info.
```ruby
public static void editProfile(String id, String info){
        File inputFile = new File("./Profiles.txt");
        try{
            Scanner in = new Scanner(inputFile);
            while(in.hasNextLine()){
                String string = in.nextLine();
                String[] stringArray = string.split(",");
                if(id.equals(stringArray[0])) {
                }
            }
            Logger.logInfo("Successfully read profile");
        }
        catch (FileNotFoundException ex){
            Logger.logError("Failed to read profile");
            System.out.println("FileNotFoundException: " + ex.getMessage());
        }
```
- Connecting to the actual database uses 2 design patterns (Singleton, Factory). The factory class "Database" handles all incoming connection requests and routes them to the corresponding connection type which is handled then by each connection class (MySql,Sqlite,Oracle). Using the factory design pattern meant for greater scalability (more connection types can be added in the future and sent to the factory class).
```ruby
 public static Connection createConnection(String connectionType,ConnectionInfo connectionInfo){
        try {
            if (connectionType.equals(ConnectionType.MySql)) {
                Logger.logInfo("MySql connection created");
                return MySQL.getInstance(connectionInfo);
            }
            if (connectionType.equals(ConnectionType.Sqlite)) {
                Logger.logInfo("Sqlite connection created");
                return SQLite.getInstance(connectionInfo);
            }
            if (connectionType.equals(ConnectionType.Oracle)) {
                Logger.logInfo("Oracle connection created");
                return Oracle.getInstance(connectionInfo);
            }
                Logger.logError("Failed to create connection");
                throw new DatabaseException("Unsupported Schema");
        }
        catch (DatabaseException ex) {
            ex.getMessage();
            return null;
        }
```
- As stated above, the "MySQL" class uses the singleton design pattern (Used to only handle or connect to a Mysql connection at a time). This class holds all the sql exeuction, data exportation, and version control methods. Other connection types follow suit of this class.
```ruby
public class MySQL{
    protected static ConnectionInfo connectionInfo1;
    protected static Connection instance;
    protected static Statement statement;
    protected static Connection getInstance(ConnectionInfo connectionInfo){
        try{
            connectionInfo1 = connectionInfo;
            Class.forName("com.mysql.cj.jdbc.Driver");
            instance =DriverManager.getConnection("jdbc:mysql://localhost:" + connectionInfo.port + "/"
                    + connectionInfo.dbName,connectionInfo.username, connectionInfo.password);
            return instance;
        }catch(Exception e){
            System.out.println(e);
            return null;
```
- For the database management we used seperate sql delete methods (one for deleting a row in a specified table, one for deleting a specified column in a specified table, and one for deleting a table using the table's name).
```ruby
public static void deleteStatement(String tableName, int id){
        String sql = "DELETE FROM " + tableName + " where id = ?";
        try{
            PreparedStatement stmt = instance.prepareStatement(sql);
            // set the corresponding param
            stmt.setInt(1, id);
            // execute the delete statement
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteColumn( String column) {
                String query = "ALTER TABLE tableName DROP column = ?";
                try{
                    Statement stmt = instance.createStatement();
                    // set the corresponding param
                    // execute the delete statement
                    stmt.executeUpdate(query);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            public void deleteTable(){
                String sql = "DROP TABLE tableName";

                try{
                    Statement stmt = instance.createStatement();
                    // execute the delete statement
                    stmt.executeUpdate(sql);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
```
- Updating a certain row in the database requires the table name and the specified id for that row to update. The execution for that sql statement is the same as the previous ones. The option for renaming a table's name using its name as parameters was also used.
```ruby
public void updateStatement(Integer id){
        String query=" UPDATE tableName SET = ? = ? where id = ?" ;
        try{
            Statement stmt = instance.createStatement();
            // execute the delete statement
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void renameTable() throws SQLException {
            ResultSet result = null;

            String newTableName = result.getString("new_name");
            String query= "RENAME TABLE tableName TO newTableName";

            try{
                Statement stmt = instance.createStatement();

                // execute the delete statement
                stmt.executeUpdate(query);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
		public static void selectStatement(String tableName){
        try {
            PreparedStatement ps = instance.prepareStatement("SELECT * from " + tableName);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString(1));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
```
- Finally, the "closeConnection()" method was added to make sure that no connections were kept in the background when the users request to end the session. Each connection type has the "closeConnection()" method which are handled by the factory class "Database".
```ruby
public static void closeConnection() {
        if (instance != null) {
            try {
                instance.close();
                System.out.println("Connection Closed!");
            } catch (SQLException e) {
                e.printStackTrace();
```
```ruby
public static void closeConnection(String connectionType){
            if(connectionType.equals(ConnectionType.MySql)){
                Logger.logInfo("Closed Connection");
                MySQL.closeConnection();
```
## Data Importation
- Our main focus was the "CSVFileAnalyzer()" which uses the opencsv library the handles writing the csv files, and helps in parsing the csv imported file to an sql format.
```ruby
public class CsvFileAnalyzer implements FileAnalyzer {
    
    private final DatabaseManager databaseManager;
    
    public CsvFileAnalyzer(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    
    @Override
    public long analyseFile(File file, String tableName) {
        long rowsInserted = 0;
        databaseManager.connect();
        
        try (
                FileWriter fileWriter = new FileWriter(new File("D:\\My_Work_Area\\DatabaseManager\\test\\test.sql"))
        ) {
            CSVReader reader = new CSVReader(new FileReader(file));
            List<String[]> records = reader.readAll();
            String columnsNamesPart = "(" + String.join(",", records.get(0)) + ")";
            records.remove(0);
            for (String[] recordFields : records) {
                String insertStmt = getTableInsertStmt(tableName, columnsNamesPart, recordFields);
                boolean insertOperationResult = databaseManager.executeInsertStmt(insertStmt);
                if (insertOperationResult) {
                    writeExecutedSqlStatementsIntoFile(fileWriter, insertStmt);
                    rowsInserted++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        } finally {
            databaseManager.closeStatement();
            databaseManager.closeConnection();
        }
        
        return rowsInserted;
    }
```
- This class also uses an sql insertion method to insert the csv-generated sql statements from the above method to execute into the database. Since saving the imported file as a csv one was also a part of the requirements. We added the the parsed sql statements to the imported/saved file.
```ruby
private String getTableInsertStmt(String tableName, String columnsNamesPart, String[] fieldsValues) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String value : fieldsValues) {
            stringJoiner.add("'" + value + "'");
        }
        return "insert into " +
                tableName +
                " " +
                columnsNamesPart +
                " values(" +
                stringJoiner +
                ")";
    }
    
    private void writeExecutedSqlStatementsIntoFile(FileWriter fileWriter, String insertStmt) {
        try {
            fileWriter.write(insertStmt + ";\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```

## Exporting data and schema
- Exporting a database's schema and data was done using the mysqldump command line commands. It uses the connection info as a parameter than injects them into the runtime execution. A string[] was used to be able to take advantage of window's cmd along with the correct mysqldump location and commands. The exported file is saved into the user's documents folder in csv format.
```ruby
protected static void exportData(Connection connection){
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c",
                    "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump\" -u" +
                            connectionInfo1.username + " -p" +
                            connectionInfo1.password + " " + connectionInfo1.dbName + " > C:\\Users\\ahmad\\Documents\\" + connectionInfo1.dbName +".csv"});
            if(exec.waitFor()==0)
            {
                //normally terminated, a way to read the output
                InputStream inputStream = exec.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);
            }
            else
            {
                // abnormally terminated, there was some problem
                //a way to read the error during the execution of the command
                InputStream errorStream = exec.getErrorStream();
                byte[] buffer = new byte[errorStream.available()];
                errorStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
```
## Version Control
- For Snapshot taking the "takeSnapshot()" method is utilized. Just like export data method was done using the command line command for mysqldump, it was also used here but to save an sql file format to the documents folder so it can later be imported or restored to the database when the user requests.
```ruby
public static void takeSnapshot() {
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c",
                    "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump\" -u" +
                    connectionInfo1.username + " -p" +
                    connectionInfo1.password + " " + connectionInfo1.dbName + " > C:\\Users\\ahmad\\Documents\\" + connectionInfo1.dbName +".sql"});
            if(exec.waitFor()==0)
            {
                //normally terminated, a way to read the output
                InputStream inputStream = exec.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);
            }
            else
            {
                // abnormally terminated, there was some problem
                //a way to read the error during the execution of the command
                InputStream errorStream = exec.getErrorStream();
                byte[] buffer = new byte[errorStream.available()];
                errorStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
```
- The "importSnapshot()" method takes a string as the file's name for a parameter and restores the contents of the file back to the database using the mysql command in the command line.
```ruby
protected static void importSnapshot(String s) {
        try {
            Process exec = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c",
                    "\"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql\" -u" +
                            connectionInfo1.username + " -p" +
                            connectionInfo1.password + " " + connectionInfo1.dbName + " < C:\\Users\\ahmad\\Documents\\" + s});
            if(exec.waitFor()==0)
            {
                //normally terminated, a way to read the output
                InputStream inputStream = exec.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);
            }
            else
            {
                // abnormally terminated, there was some problem
                //a way to read the error during the execution of the command
                InputStream errorStream = exec.getErrorStream();
                byte[] buffer = new byte[errorStream.available()];
                errorStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
```
## User Authentication
- The user authentication houses 2 componenets which communicate with each other to send out a simple gui for the user login. The "Lab1frame" component is the backend for the authentication which specifies the panels (username, password) and compares the user-inputted credentials to the existing accounts found in the "USERDATA.txt". We've also added an id for the usertype in the text file (0 = admin, 1 = staff, 2 = reader) that is sent to the main which checks for the user's type and prints out the corresponding functions that can be done.
```ruby
private void createComponents() throws FileNotFoundException
	{
		userNameField = new JTextField(10);
		passwordField = new JTextField(10);

		JLabel userNameLabel = new JLabel("User Name");
		JLabel passwordLabel = new JLabel("Password");

		JButton loginButton = new JButton("Login");
		JButton exitButton = new JButton("Exit");

		JPanel panel = new JPanel();
		panel.add(userNameField);
		panel.add(userNameLabel);
		panel.add(passwordField);
		panel.add(passwordLabel);
		panel.add(loginButton);
		panel.add(exitButton);
		add(panel);

		ActionListener exitListener = new ClickListener1();
		ActionListener loginListener = new ClickListener2();
		exitButton.addActionListener(exitListener);
		loginButton.addActionListener(loginListener);
	}
	public class ClickListener1 implements ActionListener
	{

		public void actionPerformed(ActionEvent event)
		{
			System.exit(0);
		}
	}
	public static class ClickListener2 implements ActionListener
	{

		public void actionPerformed(ActionEvent event)
		{
			File inputFile = new File("USERDATA.txt");

			String userNameInput = userNameField.getText();

			String passwordInput = passwordField.getText();

			try {
				Scanner in = new Scanner(new File("USERDATA.txt"));
				while (in.hasNextLine())
				{
					String s = in.nextLine();
					String[] sArray = s.split(",");

					System.out.println(sArray[0]); //Just to verify that file is being read
					System.out.println(sArray[1]);

					userType = sArray[2];



					if (userNameInput.equals(sArray[0]) && passwordInput.equals(sArray[1]))
					{
						JOptionPane.showMessageDialog(null,
								"Login Successful", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(null,
								"Invalid Username / Password Combo", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}

				in.close();

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,
						"User Database Not Found", "Error",
						JOptionPane.ERROR_MESSAGE);
```
- The Lab1Viewer contains the "loginAuth()" method that calls an object from the previously mentioned class "Lab1frame". This class is later sent into the main to be run as whole application
```ruby
public class Lab1Viewer {
    public static void loginAuth() {
        try {
            JFrame frame = new Lab1Frame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (FileNotFoundException ex) {
            ex.getMessage();
```
- We added a start command to wait for the user login to grab the needed id that is used in identifying the user's type and printing out the needed functions.
```ruby
 if (startCommand.equals("start")) {
            if (Lab1Frame.userType.equals("1")) {

                Database database = new Database();
                ConnectionInfo connectionInfo = null;

                System.out.println("1- Choose existing profile\n" +
                        "2- Create new Profile\n" +
                        "Choice: ");
                int choice1 = sc.nextInt();
```
## Logging
- The Singleton design pattern was used for the logging component as creating more than one object for the logger would cause conflicts and repetitive logging between all the logging objects. Each method (LogInfo, LogWarning, LogDebug, LogError) are utlilized when in classes when logging is needed. It saves the logs to an array which is contents are later saved to the "logs.txt" file at the end of the application using the "saveLogs()" method.
```ruby
public class Logger {
    private static Logger instance;
    static ArrayList<String> logsArray = new ArrayList<String>(5);

    private Logger() {

    }

    public static Logger getInstance() {
        if(instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    public static void logInfo(String message) { logsArray.add(java.time.LocalDateTime.now()  + " [INFO] " + message); }

    public static void logDebug(String message) {
        logsArray.add(java.time.LocalDateTime.now()  + " [Debug] " + message);
    }

    public static void logWarning(String message) {
        logsArray.add(java.time.LocalDateTime.now()  + " [Warn] " + message);
    }

    public static void logError(String message) {
        logsArray.add(java.time.LocalDateTime.now()  + " [Error] " + message);
    }
    public static void SaveLogs(){
        try{
            FileWriter out = new FileWriter("./logs.txt",true);
            for(int i =0;i<logsArray.size();i++)
                out.write(logsArray.get(i) + "\n");

            out.close();
```
