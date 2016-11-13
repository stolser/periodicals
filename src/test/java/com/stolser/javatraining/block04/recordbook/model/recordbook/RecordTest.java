package com.stolser.javatraining.block04.recordbook.model.recordbook;

import com.stolser.javatraining.block04.recordbook.model.user.UserAddress;
import com.stolser.javatraining.block04.recordbook.model.user.UserGroup;
import com.stolser.javatraining.block04.recordbook.model.user.UserName;
import com.stolser.javatraining.block04.recordbook.model.user.UserPhone;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static com.stolser.javatraining.block04.recordbook.model.user.UserGroup.FAMILY;
import static com.stolser.javatraining.block04.recordbook.model.user.UserGroup.FRIENDS;
import static com.stolser.javatraining.block04.recordbook.model.user.UserPhone.UserPhoneType.HOME;
import static org.junit.Assert.assertEquals;

public class RecordTest {
    private UserName userName;
    private Set<UserGroup> groups;
    private UserPhone phone1;
    private UserPhone phone2;
    private Record original;
    private UserAddress address;

    @Before
    public void setUp() throws Exception {
        userName = new UserName("Oleg", "Stoliarov");
        groups = new HashSet<UserGroup>() {
            {
                add(FAMILY);
                add(FRIENDS);
                add(UserGroup.WORK);
            }
        };

        phone1 = new UserPhone("67", "123 45 67", true, HOME);
        phone2 = new UserPhone("44", "123 00 22", false, UserPhone.UserPhoneType.WORK);

        original = new Record(userName);
        groups.forEach(original::addGroup);
        original.addPhone(phone1);
        original.addPhone(phone2);
    }

    @Test
    public void cloneShouldMakeDeepCopy() throws Exception {
        Record clone = original.clone();

        userName.setFirstName("FirstName");
        userName.setLastName("LastName");
        assertEquals("FirstName", original.getUserName().getFirstName());
        assertEquals("LastName", original.getUserName().getLastName());
        assertEquals("Oleg", clone.getUserName().getFirstName());
        assertEquals("Stoliarov", clone.getUserName().getLastName());

        original.removeGroup(FAMILY);
        original.removeGroup(FRIENDS);
        assertEquals(1, original.getGroups().size());
        assertEquals(3, clone.getGroups().size());

        original.removePhone(phone1);
        original.removePhone(phone2);
        assertEquals(0, original.getPhones().size());
        assertEquals(2, clone.getPhones().size());
    }

    @Test
    public void updateUserNameShouldCallAllGettersOnArgument() {

    }
}