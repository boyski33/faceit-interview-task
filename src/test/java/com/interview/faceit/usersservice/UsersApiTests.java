package com.interview.faceit.usersservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.faceit.usersservice.core.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UsersApiTests {

  private static final String USERS_API_URL = "/users";

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  public void given_user_json_valid_when_addUser_should_return_201_created() throws Exception {
    JSONObject userJson = getStubUserJson();

    mockMvc.perform(post(USERS_API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(userJson.toString())
    ).andExpect(status().isCreated());
  }

  @Test
  public void given_user_already_exists_when_addUser_should_return_400_bad_request() throws Exception {
    JSONObject userJson = getStubUserJson();

    // insert once
    mockMvc.perform(post(USERS_API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(userJson.toString()));

    // insert again should return 400
    mockMvc.perform(post(USERS_API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(userJson.toString())
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void given_partial_query_when_getUsers_should_return_user_and_200_ok() throws Exception {
    JSONObject userJson = getStubUserJson();
    userJson.put("firstName", "Christopher");
    String queryWithPartialName = "?firstName=stopher";

    // given inserted user
    mockMvc.perform(post(USERS_API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(userJson.toString()));

    // when querying users with partial name
    MvcResult result = mockMvc.perform(get(USERS_API_URL + queryWithPartialName))
        .andExpect(status().isOk())
        .andReturn();

    List users = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

    // then should get a result
    assertEquals(1, users.size());
  }

  @Test
  public void given_query_with_page_out_of_range_when_getUsers_should_return_400_bad_request() throws Exception {
    JSONObject userJson = getStubUserJson();
    String outOfRangeQuery = "?page=1&size=10";

    // insert user
    mockMvc.perform(post(USERS_API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(userJson.toString()));

    mockMvc.perform(get(USERS_API_URL + outOfRangeQuery))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void given_existing_user_when_modifyUser_should_return_200_ok() throws Exception {
    JSONObject userJson = getStubUserJson();

    // insert user and get its ID
    MvcResult result = mockMvc.perform(post(USERS_API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(userJson.toString()))
        .andReturn();

    User insertedUser = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);

    // update user with same ID
    mockMvc.perform(put(USERS_API_URL + "/" + insertedUser.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(userJson.toString())
    ).andExpect(status().isOk());
  }

  @Test
  public void insert_user_delete_user_insert_again_should_succeed() throws Exception {
    JSONObject userJson = getStubUserJson();
    userJson.put("nickname", "boyski");

    // insert user and get its ID
    mockMvc.perform(post(USERS_API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(userJson.toString()))
        .andExpect(status().isCreated());

    // delete user by nickname
    mockMvc.perform(delete(USERS_API_URL + "?nickname=boyski"))
        .andExpect(status().isOk());

    // insert same user should return 201 created
    mockMvc.perform(post(USERS_API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(userJson.toString()))
        .andExpect(status().isCreated());
  }

  @Test
  public void given_user_doesnt_exist_in_database_when_getById_should_return_404_not_found() throws Exception {
    UUID uuid = UUID.randomUUID();

    mockMvc.perform(get(USERS_API_URL + "/" + uuid.toString()))
        .andExpect(status().isNotFound());
  }

  @Test
  public void given_user_doesnt_exist_in_database_when_delete_should_return_404_not_found() throws Exception {
    UUID uuid = UUID.randomUUID();

    mockMvc.perform(delete(USERS_API_URL + "?id=" + uuid.toString()))
        .andExpect(status().isNotFound());
  }

  @Test
  public void given_user_with_invalid_nickname_when_addUser_should_return_400_bad_request() throws Exception {
    JSONObject json = getStubUserJson();
    json.put("nickname", "nick with spaces");

    mockMvc.perform(post(USERS_API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json.toString())
    ).andExpect(status().isBadRequest());
  }

  @Test
  public void given_user_with_malformed_email_when_addUser_should_return_400_bad_request() throws Exception {
    JSONObject json = getStubUserJson();
    json.put("email", "not_valid");

    mockMvc.perform(post(USERS_API_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(json.toString())
    ).andExpect(status().isBadRequest());
  }


  private static JSONObject getStubUserJson() throws JSONException {
    JSONObject json = new JSONObject();
    json.put("id", UUID.randomUUID().toString());
    json.put("firstName", "Boyan");
    json.put("lastName", "Kushlev");
    json.put("nickname", "boyski");
    json.put("password", "password");
    json.put("email", "example@mail.com");
    json.put("country", "United Kingdom");

    return json;
  }

}
