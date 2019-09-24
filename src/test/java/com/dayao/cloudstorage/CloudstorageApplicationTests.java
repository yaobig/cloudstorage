package com.dayao.cloudstorage;



import com.dayao.cloudstorage.entity.UserEntity;
import com.dayao.cloudstorage.service.UserService;
import com.dayao.cloudstorage.service.serviceImpl.UserServiceImpl;
import com.dayao.cloudstorage.utill.ComparatorFile;
import com.dayao.cloudstorage.utill.SwiftDFS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudstorageApplicationTests {

	@Autowired
	UserServiceImpl userService;
	@Test
	public void contextLoads() {
		SwiftDFS swift = new SwiftDFS();
//		swift.createUser("dayao");
//		swift.createUser(Constants.GARBAGE_PREFIX+"dayao");
//		swift.createDir("dayao","123");
//		swift.createDir("dayao","123456/");
//		UserEntity user = userService.getUserByname("dayao");
		//List s = userService.userList();
	}
	@Test
	public void getAllStoredList(){
		String username = "dayao";
		SwiftDFS swiftdfs = new SwiftDFS();
		String up =null;
		up = username+"/";
		List list =swiftdfs.getFile(up);
		ComparatorFile comparator = new ComparatorFile();
		if (!list.isEmpty()) {
			synchronized (list) {
				Collections.sort(list, comparator);
			}

		}
		System.out.println();
	}
	/*@Test
	public void connect() {
		OSClient os = OSFactory.builderV2()
				.endpoint("http://192.168.1.200:5000/v2.0")
				.credentials("admin", "000000")
				.tenantName("admin")
				.authenticate();
		List list = os.objectStorage().objects().list("admin/");
		System.out.println(list.toString());
	}*/
}
