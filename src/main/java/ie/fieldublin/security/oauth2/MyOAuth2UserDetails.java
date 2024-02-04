package ie.fieldublin.security.oauth2;

import ie.fieldublin.domain.role.entity.Role;
import ie.fieldublin.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class MyOAuth2UserDetails implements OAuth2User {

    private final User user;

    public MyOAuth2UserDetails(User user) {
        this.user = user;
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return authorities;
    }


    public Set<Role> getRoles() {
        return user.getRoles();
    }

    @Override
    public String getName() {
        return user.getFirstname();
    }
}
