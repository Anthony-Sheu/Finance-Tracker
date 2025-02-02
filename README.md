# Personal Finance Tracker

## Application Description
This is a Java program designed to keep track of your personal finances. It's primary usage is to show you all your banking information/transactions from a simple GUI without having to go through all the hassle of logging onto online banking platforms. You can input transactions categorized by date, store/vendor, expense category, and account you spent the money from. This will automatically update your running balances in the main menu which can help you with monitoring your expenses. 

## Instructions for End User
At the start of the program, you will be prompted to choose to either run the text based version of the program or the GUI version of the program. Both use the same data so choose whichever you want. If you wish to **add an X to a Y**, then after loading into the main menu, go ahead and click Transactions -> add a transaction. This will then prompt you to enter the information for a new transaction. To remove or edit a transction, you can find the corresponding buttons in the same transaction menu. You will be shown a list of your saved transctions and be prompted to enter which line you would like to remove/edit. You then have 3 options to view transactions, **Show all transactions**, **Show all transactions in a specific month/year**, and **Show all transactions under a specific expense category**. To find the graph that shows your monthly spendings by year, start from the main menu, and go ahead and click **Display graph**. You will then be prompted to choose a year in which to view, and a graph will be subsequently displayed. To load past saved information, just simply start the program (in either text or GUI) and follow the instructions on screen prompting you to either load or not load the information. Same goes for saving the information, after you are done using the program, press 5 to quit in the text version or click **Quit** in the GUI and you will be swiftly prompted with save or not save your banking and transaction information.

## Usage
This program is targeted towards anyone who has a bank account. Owning a bank account means that you must take the responsibilty of managing your funds, not overdrafting, and paying your bills on time. This app is designed to make this daunting task easier for you. Your bank balance and transaction history all available within a simple program.

## Personal Interest
Ever since I opened my first bank account and started working at 16, my dad has drilled the importance of financial responsibility into my brain like it was the the virtue of life (objectively it is). So since then, I've been using an Excel sheet to keep track of my finances, but never attempted to transition to an interactive program. This is my chance to my passion into a project that I hope others could also find a use for.

## Application Features
- Keeps track of bank balances across multiple accounts/bank institutions
- Logs transactions that can be categorized by date, store/vendor, and expense type
- Logs income that can be categorized by date, source, and income type
- Displays your total monthly spendings as well as individual expense category spending trends

## User Stories
- As a user, I want to be able to add a transaction to a list of transactions with the same expense category
- As a user, I want to be able to view a list of all my transactions categorized under one expense
- As a user, I want to be able to check all my spendings within a specific month
- As a user, I want to be able to set a limit on my monthly spendings for a specific category
- As a user, I want to be able to transfer amounts between accounts
- As a user, I want to be able to edit a previous transaction
- As a user, I want to be able to hide a transaction but still make it valid
- As a user, I want to be able to visualize my spending trends
- As a user, I want to be able to add/remove expense categories
- As a user, I want to be able to have the choice to save all my transactions and banking information to a file
- As a user, I want to be able to load my transactions and banking information from a previous session

## Phase 4: Task 2
Mon Nov 25 14:43:43 PST 2024  
new bank account added  
Mon Nov 25 14:43:43 PST 2024  
new bank account added  
Mon Nov 25 14:43:43 PST 2024  
New transaction added to tracker  
Mon Nov 25 14:43:55 PST 2024  
New transaction added to tracker  
Mon Nov 25 14:44:00 PST 2024  
Amount refunded  
Mon Nov 25 14:44:00 PST 2024  
old transaction removed from tracker  
Mon Nov 25 14:44:12 PST 2024  
New transaction added to tracker  
Mon Nov 25 14:44:18 PST 2024  
Amount refunded  
Mon Nov 25 14:44:18 PST 2024  
Updated old transaction amount in tracker list  
Mon Nov 25 14:44:55 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:44:58 PST 2024  
list of transactions sorted by expense category  
Mon Nov 25 14:44:59 PST 2024  
list of transactions sorted by expense category  
Mon Nov 25 14:45:11 PST 2024  
new bank account added  
Mon Nov 25 14:45:14 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:45:14 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:45:14 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:45:14 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:45:14 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:45:14 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:45:14 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:45:14 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:45:14 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:45:14 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:45:14 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:45:14 PST 2024  
list of transactions sorted by month/year  
Mon Nov 25 14:45:22 PST 2024  
chequeing account updated  
Mon Nov 25 14:45:22 PST 2024  
chequeing account updated  
Mon Nov 25 14:45:22 PST 2024  
transferred between two accounts  

## Phase 4: Task 3
For my project, I can confidently say that it was really badly factored in the beginning. However, as the course went on and I got to learn more about proper software construction, I can also confidently say that the later half of my project was better formatted than the first. To start off, I have way too many Reader and Writer classes. All these classes do is take in a JSON file and convert it into information (for example bank account and transaction information). This could have been implemented in the Banks or Transaction classes themselves. One other thing that I would also like to improve is the format of how I utilized JPanels. Working through phase 3 made me realize that I happened to write a lot of recurring code in the ...Panel.java classes which I could have easily moved to PanelManager. One example is the backButton JButton which every panel has, which returns the user back to the main menu. This button could have been easily initialized in the constructor of PanelManager instead of being initialized in every single menu panel. Aside from just back button, I also had buttons for every single expense category. To create the button, I would have to loop through the list of Expense and create a button every single one. I realized too late that I actually create these expense buttons like 2 or 3 times throughout the program, which could have been easily refactored into one initial call (then adding new ActionListeners afterwards).