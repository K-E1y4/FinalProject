package FinalProject2.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalProject2.model.Employee;
import FinalProject2.model.UserAccount;
import FinalProject2.repository.EmployeeRepository;

@Service
public class UserAccountService implements UserDetailsService {
	
    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	       if (username == null || "".equals(username)) {
	            throw new UsernameNotFoundException("Username is empty");
	        }
	       
	        Optional<Employee> employee = repository.findById(username);
	        Employee emp = employee.get();
	        if (emp == null) {
	            throw new UsernameNotFoundException("User not found: " + username);
	        }
	        
	        UserAccount user = new UserAccount(emp,getAuthorities(emp));

	        return user;
	}
	
	private Collection<GrantedAuthority> getAuthorities(Employee account){
		
		if(account.getAdmin_type() == '1'){
			return AuthorityUtils.createAuthorityList("ROLE_ADMIN");
		}else{
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		}
		
	}
//
//    @Transactional
//    public void registerAdmin(String username, String password) {
//        Employee user = new Employee(username, passwordEncoder.encode(password),'1');
//        repository.save(user);
//    }
//
//    @Transactional
//    public void registerUser(String username, String password) {
//        Employee user = new Employee(username, passwordEncoder.encode(password),'0');
//        repository.save(user);
//    }

}