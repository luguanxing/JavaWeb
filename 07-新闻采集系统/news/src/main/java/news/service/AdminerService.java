package news.service;

import news.entity.Adminer;


public interface AdminerService {

	public Adminer getFirstAdminer();
	
	public Boolean updateFirstAdminer(Adminer adminer);
	
	public Adminer getByUsername(String username);
	
	public Integer update(Adminer adminer);
	
}
