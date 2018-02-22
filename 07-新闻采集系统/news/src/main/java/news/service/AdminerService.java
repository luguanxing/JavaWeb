package news.service;

import news.entity.Adminer;


public interface AdminerService {

	public Adminer getByUsername(String username);
	
	public Integer update(Adminer adminer);
	
}
