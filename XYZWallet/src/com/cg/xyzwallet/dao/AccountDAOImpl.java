package com.cg.xyzwallet.dao;

import javax.persistence.EntityManager;

import com.cg.xyzwallet.bean.AccountBean;

public class AccountDAOImpl implements IAccountDao {

	
	private EntityManager em;
	
	@Override
	public boolean createAccount(AccountBean accountBean) throws Exception {
		try{
			
			em=JPAManager.createEntityManager();
			em.getTransaction().begin();
	
			
			em.persist(accountBean);
			
			em.getTransaction().commit();
		JPAManager.closeResources(em);
			
			return true;
		}catch(Exception e){
		
			e.printStackTrace();
			return false;
		}
	
	}

	/*@Override
	public boolean updateAccount(AccountBean accountBean) throws Exception {
		try{
			this.em=JPAManager.createEntityManager();
			em.getTransaction().begin();
			
			em.merge(accountBean);
			
			em.getTransaction().commit();
			JPAManager.closeResources(em);
			return true;
		}catch(Exception e){
			return false;
		}
	
	}
*/
	@Override
	public AccountBean findAccount(int accountId) throws Exception {
		try{
			em=JPAManager.createEntityManager();
			AccountBean accountBean=em.find(AccountBean.class,accountId);
			JPAManager.closeResources(em);
			return accountBean;
			
		}catch(Exception e){
		
		
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deposit(AccountBean accountBean, double depositAmount)
			throws Exception {
		boolean result=false;
		try{
			
			if(depositAmount>0){
				result=true;
			em=JPAManager.createEntityManager();
			
			accountBean.setBalance(accountBean.getBalance()+depositAmount);

			em.getTransaction().begin();
		
			em.merge(accountBean);
			
			em.getTransaction().commit();
			JPAManager.closeResources(em);
			
		}else{
		   result=false;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	  return result;
	}

	@Override
	public boolean withdraw(AccountBean accountBean, double withdrawAmount)
			throws Exception {
		try{
			em=JPAManager.createEntityManager();
			accountBean.setBalance(accountBean.getBalance()-withdrawAmount);

			em.getTransaction().begin();
			
			em.merge(accountBean);
			
			em.getTransaction().commit();
			JPAManager.closeResources(em);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public boolean fundTransfer(AccountBean transferingAccountBean,
			AccountBean beneficiaryAccountBean, double transferAmount)
			throws Exception {
		
		try{
			em=JPAManager.createEntityManager();
			
			transferingAccountBean.setBalance(transferingAccountBean.getBalance()-transferAmount);
			beneficiaryAccountBean.setBalance(beneficiaryAccountBean.getBalance()+transferAmount);
			
			em.getTransaction().begin();
			
			em.merge(transferingAccountBean);
			em.merge(beneficiaryAccountBean);
			
			em.getTransaction().commit();
			JPAManager.closeResources(em);
		    return true;
		}catch(Exception e){
			return false;
		}
	
	}

}
