package com.cg.xyzwallet.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.cg.xyzwallet.bean.AccountBean;
import com.cg.xyzwallet.bean.CustomerBean;
import com.cg.xyzwallet.bean.WalletTransaction;
import com.cg.xyzwallet.service.AccountServiceImpl;
import com.cg.xyzwallet.service.IAccountService;

public class Client {
	   
	IAccountService service=new AccountServiceImpl();
	CustomerBean customer=new CustomerBean();
	Scanner scanner=new Scanner(System.in);
	
	
	public static void main(String[] args) throws Exception {
		char ch;
		Client client=new  Client();
		do
		{
		System.out.println("***XYZ BANK***");
		System.out.println("1. Create Account ");
		System.out.println("2. Show Balance ");
		System.out.println("3. Deposit ");
		System.out.println("4. Withdraw ");
		System.out.println("5. Fund Transfer");
		System.out.println("6. Print Transactions");
		System.out.println("7. Exit");
		System.out.println("Choose an option");
		int choice =client. scanner.nextInt();
		
		switch (choice) {
		case 1:client.create();
               break;
		case 2:client.showbalance();

			break;

		case 3:client.deposit();

			break;
			
			
		case 4:client.withdraw();

			break;
			
	
		case 5:client.fundtransfer();

			break;
			
		
		case 6:client.printTransaction();

			break;
		case 7:System.exit(0);

			break;
			
			
		default:System.out.println("Invalid Option!!!!");
			break;
		}
		
		System.out.println("\nDo you want to continue press Y/N");
		ch=client.scanner.next().charAt(0);
		
		}while(ch=='y' || ch=='Y');

		
	}
	
	
	void create() throws Exception
	{     
		

		
		
		System.out.print("Enter Customer firstname\t\t:");
		String fname=scanner.next();
		
		System.out.print("Enter Customer lastname\t\t\t:");
		String lname=scanner.next();
		
		System.out.print("Enter  Customer  email id\t\t:");
		String email=scanner.next();
		
		System.out.print("Enter  Customer  phone number\t\t:");
		String phone=scanner.next();
		
		System.out.print("Enter  Customer PAN number\t\t:");
		String pan=scanner.next();
		
		System.out.print("Enter  Customer  address\t\t:");
		scanner.nextLine();
		String address=scanner.nextLine();
		
		
		CustomerBean customerBean=new CustomerBean();
	
		customerBean.setAddress(address);
		customerBean.setEmailId(email);
		customerBean.setPanNum(pan);
		customerBean.setPhoneNo(phone);
		customerBean.setFirstName(fname);
		customerBean.setLastName(lname);
		
		
		System.out.print("Enter  Account ID\t\t:");
		int accId=scanner.nextInt();
		
		System.out.print("Enter Date of Opening (DD/MM/YYYY)\t\t:");
		String accDateInput=scanner.next();
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		Date dateOfOpening = sdf.parse(accDateInput);
		
		
		
		
		System.out.print("Enter balance to create account\t\t");
		double balance=scanner.nextDouble();
		
		AccountBean accountBean=new AccountBean();
		accountBean.setAccountId(accId);
		accountBean.setBalance(balance);
		accountBean.setInitialDeposit(balance);
		accountBean.setCustomerBean(customerBean);
	
		accountBean.setDateOfOpening(dateOfOpening);
		
		
		
		
		
		try
		{

	    boolean result=service.createAccount(accountBean);
		
		if(result)
		{
			System.out.print("\n\nCongratulations your account has been created successfully...\t\t"+ "");
		}
		else
		{
			System.out.println("\n\nError in Creating your account\t\t");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	void showbalance() throws Exception 
	{
		System.out.print("Enter Account ID\t\t:");
		int accId=scanner.nextInt();
		
		AccountBean accountBean=service.findAccount(accId);
		
		if(accountBean!=null){
			double balance=accountBean.getBalance();
			
			System.out.println(accountBean.getCustomerBean());
			System.out.println("***Your balance is\t\t: " +balance+"***");
			
		}else{
			System.out.println("Account Does not exist");
		
		}
		
		
		
			
		
	}
	
	void deposit() throws Exception
	{
		System.out.print("Enter Account ID\t\t:");
		int accountId=scanner.nextInt();
		
		AccountBean accountBean=service.findAccount(accountId);
		
		
		if(accountBean!=null){
		System.out.print("Enter amount that you want to deposit\t\t");
		double depositAmt=scanner.nextDouble();
		
		WalletTransaction wt=new WalletTransaction();
		wt.setTransactionType(1);
		wt.setTransactionDate(new Date());
		wt.setTransactionAmt(depositAmt);
		wt.setBeneficiaryAccountBean(null);
		
	      accountBean.addTransation(wt);
		
		boolean result=service.deposit(accountBean, depositAmt);
		System.out.println(accountBean.getCustomerBean());
		
		if(result){
			System.out.println("Deposited Money into Account!");
			
		    System.out.println("Current Balance\t\t:"+accountBean.getBalance());
		}else{
			System.out.println("Deposit Failed! ");
		}
		
		
		}else{
			System.out.println("Account Does not exist");
		}
	}
	
	void withdraw() throws Exception
	{
		System.out.print("Enter Account ID\t\t:");
		int accId=scanner.nextInt();
		
		AccountBean accountBean=service.findAccount(accId);
		if(accountBean!=null){
		System.out.print("Enter amount that you want to withdraw\t\t:");
		double withdrawAmt=scanner.nextDouble();
	
		WalletTransaction wt=new WalletTransaction();
		wt.setTransactionType(2);
		wt.setTransactionDate(new Date());
		wt.setTransactionAmt(withdrawAmt);
		wt.setBeneficiaryAccountBean(null);
		
	   accountBean.addTransation(wt);
	   boolean result=service.withdraw(accountBean, withdrawAmt);
	   
		System.out.println(accountBean.getCustomerBean());
		if(result){
			System.out.println("Withdraw Money from Account Completed!");
			
			 System.out.println("Current Balance\t\t:"+accountBean.getBalance());
		}else{
			System.out.println("Withdraw Money from Account Failed!");
		}
		}else{
			System.out.println("Account Does not exist");
		}
		
	}
	
	void fundtransfer() throws Exception
	{
		System.out.print("Enter Account ID to Transfer Money From\t\t:");
		int srcAccId=scanner.nextInt();
		
		AccountBean accountBean1=service.findAccount(srcAccId);
		
		
		
		System.out.print("Enter Account ID to Transfer Money to\t\t:");
		int targetAccId=scanner.nextInt();
		
		AccountBean accountBean2=service.findAccount(targetAccId);
		
		System.out.print("Enter amount that you want to transfer\t\t:");
		double transferAmt=scanner.nextDouble();
		
		WalletTransaction wt=new WalletTransaction();
		wt.setTransactionType(3);
		wt.setTransactionDate(new Date());
		wt.setTransactionAmt(transferAmt);
		wt.setBeneficiaryAccountBean(accountBean2);
		
		accountBean1.addTransation(wt);
		
		
		
		boolean result=service.fundTransfer(accountBean1, accountBean2, transferAmt);
		
		if(result){
			System.out.println("Transfering Money from Account done");
		}else{
			System.out.println("Transfering Money from Account Failed ");
		}
		
	}
	
	
	void printTransaction() throws Exception
	{
		System.out.print("Enter Account ID \t\t:");
		int accId=scanner.nextInt();
		
		AccountBean accountBean=service.findAccount(accId);
		
		List<WalletTransaction>  transactions=accountBean.getAllTransactions();
		
	
		System.out.println(accountBean.getCustomerBean());
		System.out.println("***Transactions***");
		
		System.out.println("------------------------------------------------------------------");
		
		for(WalletTransaction wt:transactions){
			
			String str="";
			if(wt.getTransactionType()==1){
				str=str+"DEPOSIT";
			}
			if(wt.getTransactionType()==2){
				str=str+"WITHDRAW";
			}
			if(wt.getTransactionType()==3){
				str=str+"FUND TRANSFER";
			}
			
			str=str+"\t\t"+wt.getTransactionDate();
			
			str=str+"\t\t"+wt.getTransactionAmt();
			System.out.println(str);
		}
		
		System.out.println("------------------------------------------------------------------");
	    System.out.println("Current Balance\t\t:"+accountBean.getBalance());
	}
	
	    
	
}
