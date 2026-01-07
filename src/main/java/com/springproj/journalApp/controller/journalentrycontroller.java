//package com.springproj.journalApp.controller;
//
//import com.springproj.journalApp.entity.journalentry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//@RestController
//@RequestMapping("/journal")
//public class journalentrycontroller {
//
//    private Map<Long ,journalentry> journalentries = new HashMap<>();
//    //this is just a feild if we restart the application all the data erases
//
//
////of we put get request we come here
//    //https://localhost:8080/journal GET
//    @GetMapping
//    public List<journalentry> getAll(){
//        return new ArrayList<>(journalentries.values());
//    }
//
////methods inside a controller class should be public so that
//// they can be accessed and invoked by spring framework ot external http request
//
//
////    https://localhost:8080/journal POST
// @PostMapping
//  public boolean createentry(@RequestBody journalentry myentry){
////@requestbody is like  saying hey spring please take this data from the
//// request and turn it into a java object that i can use in my code
//journalentries.put(myentry.getId(), myentry);
//return true;}
//
//
//
//    @GetMapping("id/{myid}")
//    public journalentry getjournalentrybyid(@PathVariable Long myid){
//        return journalentries.get(myid);
//
//
//    }
////path var --id/suyash and query param id?name=x
//
//
//
//    @DeleteMapping("/id/{myid}")
//    public journalentry deletejournalentrybyid(@PathVariable Long myid){
//        return journalentries.remove(myid);}
//
//    @PutMapping("/id/{myid}")
//    public journalentry updatejournalbyid(@PathVariable Long myid, @RequestBody journalentry myentry) {
//
//        if (!journalentries.containsKey(myid)) {
//            return null;
//        }
//
//        myentry.setId(myid);
//        journalentries.put(myid, myentry);
//        return myentry;
//    }
//
//}
