package com.project.decider.controller;

import com.project.decider.dto.RecordDto;
import com.project.decider.model.*;
import com.project.decider.service.OptionService;
import com.project.decider.service.RecordService;
import com.project.decider.service.UserService;
import com.project.decider.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Lobster on 30.04.16.
 */

@Controller
@RequestMapping("/")
public class MainController {

    private final String IMAGE_FOLDER = "/images";
    private final String SERVER_EMAIL = "vla.loboda@gmail.com";
    private final String VERIFY_EMAIL = "/verify_email";
    private final String FILE_PREFIX = "IMG_";
    private final String FILE_SUFFIX = ".jpg";

    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private OptionService optionService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private HttpServletRequest request;

    private Session session;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showWelcomePage() {
        return "index";
    }

    @RequestMapping(value = "/get_user", method = RequestMethod.GET)
    @ResponseBody
    public String getUser(@RequestParam String name, @RequestParam String password) {
        return "index";
    }

    @RequestMapping(value = "/sign_up", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Void> signUp(@RequestParam String name, @RequestParam String password, @RequestParam String email){
        User user = userService.getUserByName(name);
        if (user != null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } else {
            user = userService.getUserByEmail(email);
            if (user != null){
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            } else {
                user = new User();
                user.setUsername(name);
                user.setPassword(password);
                user.setEmail(email);
                user.setVerificationCode(user.hashCode());
                userService.saveUser(user);

                sendVerifyingEmail(email, user);

                return new ResponseEntity<Void>(HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/sign_in", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> signIn(@RequestParam String name, @RequestParam String password) {
        User user = userService.getUser(name, password);
        if (user != null && user.isVerified()) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = VERIFY_EMAIL+"/{verificationCode}", method = RequestMethod.GET)
    @ResponseBody
    public String verifyEmail(@PathVariable(value="verificationCode") Integer verificationCode){
        User user = userService.getUserByVerificationCode(verificationCode);
        if (user != null) {
            user.setVerified(true);
            userService.saveUser(user);
            return "verify_success";
        } else {
            return "verify_error";
        }
    }

    @RequestMapping(value = "/check_name", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> checkUser(@RequestParam String name) {
        User user = userService.getUserByName(name);
        if (user == null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/check_email", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> checkEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/remind_info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> remindInfo(@RequestParam String email) {
        System.out.println("GET EMAIL");
        User user = userService.getUserByEmail(email);
        if (user != null) {
            if (sendRemindEmail(email, user)) {
                System.out.println("EMAIL SENT");
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
        }
        System.out.println("EMAIL NOT SENT");
        return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/get_user_info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUserInfo(@RequestParam String name) {
        System.out.println("GET USER INFO");
        User user = userService.getUserByName(name);
        if (user != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get_last_records", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<RecordDto>> getLastRecords(@RequestParam String targetUsername) {
        Long lastRecordId = recordService.getLastRecordId();
        if (lastRecordId == null) {
            return new ResponseEntity<List<RecordDto>>(new ArrayList<RecordDto>(), HttpStatus.OK);
        }

        List<Record> records = recordService.getRecordsRange(lastRecordId + 1, 6);

        return new ResponseEntity<>(convert(records, targetUsername), HttpStatus.OK);
    }


    @RequestMapping(value = "/get_next_records", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<RecordDto>> getNextRecords(@RequestParam Long recordId, @RequestParam String targetUsername) {
        List<Record> records = recordService.getRecordsRange(recordId, 6);

        if (!records.isEmpty()) {
            return new ResponseEntity<>(convert(records, targetUsername), HttpStatus.OK);
        } else {
            return new ResponseEntity<List<RecordDto>>(new ArrayList<RecordDto>(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get_last_user_records", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<RecordDto>> getLastRecordsByName(@RequestParam String requestedUsername, @RequestParam String targetUsername) {
        Long lastRecordId = recordService.getLastRecordIdByName(requestedUsername);
        if (lastRecordId == null) {
            return new ResponseEntity<List<RecordDto>>(new ArrayList<RecordDto>(), HttpStatus.OK);
        }

        List<Record> records = recordService.getRecordsRangeByName(requestedUsername, lastRecordId, 9, true);

        return new ResponseEntity<>(convert(records, targetUsername), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_next_user_records", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<RecordDto>> getNextRecordsByName(@RequestParam String requestedUsername, @RequestParam Long recordId, @RequestParam String targetUsername) {
        List<Record> records = recordService.getRecordsRangeByName(requestedUsername, recordId, 9, false);

        return new ResponseEntity<>(convert(records, targetUsername), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getUsers(@RequestParam String searchQuery) {
        return new ResponseEntity<List<User>>(userService.getUsersByTemplate(searchQuery, 10), HttpStatus.OK);
    }

    @RequestMapping(value = "/get_users_from_id", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<User>> getUserFromId(@RequestParam String searchQuery, @RequestParam String lastUsername) {
        return new ResponseEntity<List<User>>(userService.getUsersByTemplateFromUsername(searchQuery, lastUsername, 10), HttpStatus.OK);
    }

    @RequestMapping(value = "/save_vote", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<RecordDto> saveVote(@RequestParam String userName, @RequestParam Long recordId, @RequestParam String optionName) {
        Vote vote = voteService.getVoteByRecordIdUsername(recordId, userName);

        if (vote == null) {
            User user = userService.getUserByName(userName);
            Option option = optionService.getOptionByNameRecordId(optionName, recordId);
            vote = new Vote(new VoteId(user, option));
            vote = voteService.saveVote(vote);
            voteService.increaseVoteCount(option.getVoteCount());
        }

        RecordDto recordDto = convert(vote.getVoteId().getOption().getOptionId().getRecord(), userName);
        return new ResponseEntity<RecordDto>(recordDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/add_record", method = RequestMethod.POST)
    @ResponseBody
    public void addRecord(@RequestParam("files") List<MultipartFile> files,
                          @RequestParam("options") List<String> options,
                          @RequestParam("name") String name,
                          @RequestParam("description") String description) {

        User user = userService.getUserByName(name);

        Record record = new Record();
        record.setUsername(name);
        record.setDescription(description);
        record.setAvatar(user.getAvatar());

        Set<Image> images = new LinkedHashSet<>();
        for (MultipartFile file: files){
            if (!file.isEmpty()) {
                try {
                    String dirPath = request.getSession().getServletContext().getRealPath(IMAGE_FOLDER);
                    File dir = new File(dirPath);
                    if (!dir.exists()) {
                        dir.mkdir();
                    }

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
                    File newFile = File.createTempFile(FILE_PREFIX+timeStamp+"_", FILE_SUFFIX, dir);
                    file.transferTo(newFile);

                    images.add(new Image(newFile.getName(), record));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        record.setImages(new ArrayList<Image>(images));

        Set<Option> optionsEntities = new LinkedHashSet<>();
        for (String optionName : options) {
            optionsEntities.add(new Option(new OptionId(optionName, record)));
        }
        record.setOptions(new ArrayList<Option>(optionsEntities));

        recordService.saveRecord(record);
    }

    @RequestMapping(value = "/update_background", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> getBackground(@RequestParam("background") MultipartFile file, @RequestParam("name") String name) {
        User user = null;
        if (!file.isEmpty()) {
            try {
                String dirPath = request.getSession().getServletContext().getRealPath(IMAGE_FOLDER);
                File dir = new File(dirPath);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
                File newFile = File.createTempFile(FILE_PREFIX+timeStamp+"_", FILE_SUFFIX, dir);
                file.transferTo(newFile);
                user = userService.setBackground(name, newFile.getName());
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/update_avatar", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> getAvatar(@RequestParam("avatar") MultipartFile file, @RequestParam("name") String name) {
        User user = null;
        if (!file.isEmpty()) {
            try {
                String dirPath = request.getSession().getServletContext().getRealPath(IMAGE_FOLDER);
                File dir = new File(dirPath);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
                File newFile = File.createTempFile(FILE_PREFIX+timeStamp+"_", FILE_SUFFIX, dir);
                file.transferTo(newFile);
                user = userService.setAvatar(name, newFile.getName());
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
    }

    @PostConstruct
    private void initEmail(){
        Properties properties = new Properties();
        properties.put("mail.smtp.user", SERVER_EMAIL);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.debug", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "587");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SERVER_EMAIL, "09v67l85a29d01");
            }
        });
    }

    private boolean sendRemindEmail(String toEmail, User user){
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SERVER_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("WhichOne App");
            message.setText("Name: " + user.getUsername() + '\n' + "Password: " + user.getPassword());

            Transport transport = session.getTransport("smtps");
            transport.connect("smtp.gmail.com", 465, "username", "password");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        }catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }

        return true;
    }

    private String buildUri(){
        try {
            URI uri = new URI(request.getScheme(), null, request.getLocalName(), request.getLocalPort(), request.getContextPath(), null, null);
            return uri.toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean sendVerifyingEmail(String toEmail, User user){
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SERVER_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("WhichOne App");
            message.setText("Please click the link to confirm your email address: " + buildUri() + VERIFY_EMAIL + "/"+user.getVerificationCode());

            Transport transport = session.getTransport("smtps");
            transport.connect("smtp.gmail.com", 465, "username", "password");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        }catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }

        return true;
    }

    private RecordDto convert(Record record, String username) {
        Vote vote = voteService.getVoteByRecordIdUsername(record.getRecordId(), username);

        RecordDto recordDto;
        if (vote == null) {
            recordDto = new RecordDto(record, null);
        } else {
            recordDto = new RecordDto(record, vote.getVoteId().getOption().getOptionId().getOptionName());
        }

        return recordDto;
    }

    private List<RecordDto> convert(List<Record> records, String username) {
        List<RecordDto> recordsDto = new ArrayList<>();

        for (Record record : records) {
            recordsDto.add(convert(record, username));
        }

        return recordsDto;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }

    public void setOptionService(OptionService optionService) {
        this.optionService = optionService;
    }

    public void setVoteService(VoteService voteService) {
        this.voteService = voteService;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}