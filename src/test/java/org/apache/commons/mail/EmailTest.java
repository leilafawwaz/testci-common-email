package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {
	
	private static final String[] TEST_EMAILS = {"ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd"};
	
	private static final String[] Empty_Emails = {};
	
	private String[] Test_Names = {"jnsu", "juweuh", "uireui"};
	
	private String[] testValidChars = {" ", "a", "A", "\uc5ec", "0123456789", "01234567890123456789","\n" };
	
	private EmailConcrete email;
	
	@Before
	public void setUpEmailTest() throws Exception {
		
		email = new EmailConcrete();
	}
	
	@After
	public void tearDownEmailTest() throws Exception {
		
	}
	
	
	// addBcc tests
	@Test
	public void testAddBcc() throws Exception {
		
		email.addBcc(TEST_EMAILS);
		
		assertEquals(3, email.getBccAddresses().size());
	}
	
	@Test(expected = EmailException.class)
	public void testAddBccEmpty() throws Exception  {
		
		email.addBcc(Empty_Emails);
		
		assertEquals(0, email.getBccAddresses().size());
	}
	
	// addCc tests
		@Test
		public void testAddCc() throws Exception {
			
			email.addCc(TEST_EMAILS[0]);
			
			assertEquals(1, email.getCcAddresses().size());
		}
		
		@Test(expected = EmailException.class)
		public void testAddCcEmpty() throws Exception  {
			
			email.addCc("");
			
			assertEquals(0, email.getCcAddresses().size());
		}
		
		// addHeader tests
		@Test
		public void testAddHeader() throws Exception {
			
			email.addHeader(Test_Names[0] , testValidChars[3]);
			
			assertEquals(1, email.getHeaders().size());
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testAddHeaderValue() throws Exception {
			
			email.addHeader("" , testValidChars[3]);
			
			assertEquals(0, email.getHeaders().size());
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void testAddHeaderName() throws Exception {
			
			email.addHeader(Test_Names[1], "");
			
			assertEquals(0, email.getHeaders().size());
		}
		
		// addReplyTo tests
		@Test
		public void testAddReplyTo() throws Exception {
			
			email.addReplyTo(TEST_EMAILS[1], Test_Names[2]);
			
			assertEquals(1, email.getReplyToAddresses().size());
		}
		
		// buildMimeMessage tests
		@Test
		public void testBuildMimeMessage() throws EmailException {
			
			email.setHostName("local host");
			email.setSmtpPort(1234);
			email.setFrom("abc@d.com");
			email.addTo("lulu@cd.com");
			email.setSubject("test email");
			
	     	email.setContent("test content", "text/plain");
	     	
	     	email.addCc("test@b.com");
	     	email.addBcc("te@jd.com");
	     	email.addHeader("testHead", "aBc");
	     
	     	email.addReplyTo("reply@b.com");
	     	
			email.buildMimeMessage();
		}

		@Test(expected = IllegalStateException.class)
		public void testBuildMimeMessageException() throws EmailException {
			
			Properties prop = new Properties();
			Session session = Session.getDefaultInstance(prop,null);
			prop.put(EmailConstants.MAIL_HOST, "ll.host");
			
			email.setHostName("local host");
			
		    email.message=email.createMimeMessage(session);
			
			email.buildMimeMessage();
		}
		
		@Test
		public void testBuildMimeMessageNullContent() throws EmailException {
		
			email.setHostName("local host");
			email.setSmtpPort(1234);
			email.setFrom("abc@d.com");
			email.addTo("lulu@cd.com");
			email.setSubject("test email");
			
	 		
	     	email.setContent(null);
	     	
	     	
	     	email.buildMimeMessage();
	     	
		}
		
		@Test(expected=EmailException.class)
		public void testBuildMimeMessageNull() throws EmailException {
		
			email.setHostName("local host");
	     	
	     	email.buildMimeMessage();
	     	
		}
		
		@Test(expected=EmailException.class)
		public void testBuildMimeMessage2() throws EmailException {
		
			email.setHostName("local host");
	 		email.setSmtpPort(1234);
			email.setFrom("abc@d.com");
			email.setSubject("test email");
			
	     	email.setContent("test content", "text/plain");
	     	
	     	email.addHeader("testHead", "aBc");
	     
	     	email.addReplyTo("ab@co");
	     	
	     	email.buildMimeMessage();
	     	
		}
		
		// getHostName tests
		@Test
		public void testGetHostName() {
			
			email.setHostName("local host");
			String hostname = email.getHostName();
			
			assertEquals("local host", hostname);
		}
		
		@Test
		public void testGetHostNameEmpty() {
			
			email.setHostName(null);
			String hostname = email.getHostName();
			
			assertEquals(null, hostname);
		}
		
		@Test()
		public void testGetHostNameSession() {
			
			Properties prop = new Properties();
			Session session = Session.getDefaultInstance(prop,null);
			prop.put(EmailConstants.MAIL_HOST, "ll.host");
			email.setMailSession(session);
			
			assertEquals("ll.host", email.getHostName());
		}
		
		// getMailSession tests
		@Test
		public void testGetMailSession() throws EmailException {
			
			email.setHostName("testHost");
			email.setAuthentication("user", "pass");
			email.setBounceAddress("bounce@j.com");
			email.setSSLOnConnect(true);
			email.setStartTLSEnabled(true);
			email.setSSLCheckServerIdentity(true);
			
			email.getMailSession();
		}
		
		@Test(expected = EmailException.class)
		public void testGetMailSessionEmptyHost() throws EmailException {
			
			email.setHostName(null);
			
			email.getMailSession();
		}
		
		// getSentDate tests
		
		@Test
		public void getSentDate() {
			Date sentDate = new Date(172677);
			email.setSentDate(sentDate);
			
			assertEquals(sentDate, email.getSentDate());
		}
		
		@Test
		public void getSentDateNull() {
			
			java.util.Date date = new java.util.Date();
			
			// email.getSentDate();
			assertEquals (date, email.getSentDate());
		}

		// getSocketConnectionTimeout tests
		@Test
		public void testGetSocketConnectionTimeOut() {
			
			int time = 20;
			email.setSocketConnectionTimeout(time);
			
			assertEquals(time, email.getSocketConnectionTimeout());
		}
		
		 // setFrom test
		@Test
		public void testSetFrom() throws Exception {
			
			assertEquals(email, email.setFrom(TEST_EMAILS[1]));
			
		}
}
