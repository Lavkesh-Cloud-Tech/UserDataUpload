package com.lavkesh.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lavkesh.entity.User;
import com.lavkesh.repository.UserRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired private UserRepository userRepository;

  @Value("${user.upload.excelPath}")
  private String userUploadExcelPath;

  ObjectMapper mapper = new ObjectMapper();

  @Override
  public User getUser(Long userId) {
    return userRepository.getOne(userId);
  }

  @Override
  public List<User> uploadUserData() {
    List<User> userList = new ArrayList<>();
    try {
      File file = new File(userUploadExcelPath);
      FileInputStream excelFile = new FileInputStream(file);
      Workbook workbook = new XSSFWorkbook(excelFile);
      Sheet datatypeSheet = workbook.getSheetAt(0);
      Iterator<Row> iterator = datatypeSheet.iterator();

      iterator.forEachRemaining(x -> readExcelRow(userList, x));

    } catch (IOException e) {
      if (e instanceof FileNotFoundException) {
        LOGGER.error("Error in finding user data upload excel", e);
      } else {
        LOGGER.error("Error in user data upload:", e);
      }
    }

    Comparator<User> userIdComprator = Comparator.comparing(User::getUserId);
    List<User> sortedUserList =
        userList.stream().sorted(userIdComprator).collect(Collectors.toList());
    return sortedUserList;
  }

  private void readExcelRow(List<User> userList, Row row) {
    Iterator<Cell> cellIterator = row.iterator();

    Integer cellNumber = 0;
    User user = new User();
    while (cellIterator.hasNext()) {
      Cell currentCell = cellIterator.next();
      String cellValue = getCellValue(currentCell);
      if (StringUtils.isNotEmpty(cellValue)) {
        try {
          setUserData(user, cellNumber, cellValue);
        } catch (Exception e) {
          LOGGER.error("Error is read user data: {}", cellValue);
          LOGGER.error("Error is read user data: ", e);
          break;
        }
      }
      cellNumber++;
    }

    boolean isValid = validateUser(user);
    if (isValid && cellNumber != 5) {
      userList.add(user);
    } else {
      try {
        String userStr = mapper.writeValueAsString(user);
        LOGGER.error("User is not valid: ", userStr);
      } catch (JsonProcessingException e) {
        LOGGER.error("Error is parsing user: ", e);
      }
    }
  }

  private String getCellValue(Cell currentCell) {
    String value = "";
    CellType cellTypeEnum = currentCell.getCellTypeEnum();
    if (cellTypeEnum == CellType.NUMERIC) {
      value = "" + currentCell.getNumericCellValue();
    } else if (cellTypeEnum == CellType.BOOLEAN) {
      value = "" + currentCell.getBooleanCellValue();
    } else {
      value = currentCell.getStringCellValue();
    }
    return StringUtils.trimToEmpty(value);
  }

  private void setUserData(User user, Integer cellNumber, String cellValue) {
    switch (cellNumber) {
      case 0:
        Double userId = Double.valueOf(cellValue);
        user.setUserId(userId.longValue());
        break;
      case 1:
        setUserName(user, cellValue);
        break;
      case 2:
        user.setEmail(cellValue);
        break;
      case 3:
        user.setRole(cellValue);
        break;
      case 4:
        Boolean active = Boolean.valueOf(cellValue);
        user.setActive(active);
        break;
    }
  }

  private void setUserName(User user, String cellValue) {
    String[] nameArray = cellValue.split(" ");
    if (nameArray.length > 2) {
      user.setFirstName(nameArray[0]);
      user.setMiddleName(nameArray[1]);
      user.setLastName(nameArray[2]);
    } else if (nameArray.length > 1) {
      user.setFirstName(nameArray[0]);
      user.setLastName(nameArray[1]);
    } else {
      user.setFirstName(nameArray[0]);
    }
  }

  private boolean validateUser(User user) {
    boolean isValid = true;
    isValid = user != null;
    isValid = isValid && user.getUserId() != null;
    return isValid;
  }
}
