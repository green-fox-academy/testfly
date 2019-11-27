package com.greenfox.tribes1.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandlingAdvice {

  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ErrorMsg missingParams(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      errors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return new ErrorMsg("error", "Missing parameter(s): " + errors);
  }

  @ResponseBody
  @ExceptionHandler(UsernameTakenException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  ErrorMsg usernameTaken(UsernameTakenException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(NotValidKingdomNameException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  ErrorMsg notAcceptableHandler(NotValidKingdomNameException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(DateNotGivenException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  ErrorMsg dateNotGiven(DateNotGivenException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(BuildingNotValidException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  ErrorMsg notValidBuilding(BuildingNotValidException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(TroopNotValidException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  ErrorMsg notValidTroop(TroopNotValidException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(BuildingTypeNotValidException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  ErrorMsg notValidBuildingType(BuildingTypeNotValidException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(TroopTypeNotValidException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  ErrorMsg notValidTroopType(TroopTypeNotValidException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(BuildingIdNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ErrorMsg buildingIdNotFound(BuildingIdNotFoundException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(TroopIdNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ErrorMsg troopIdNotFound(TroopIdNotFoundException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(NotValidTypeException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  ErrorMsg notValidBuildingOrTroopType(NotValidTypeException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(ProgressionIdNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ErrorMsg progressionIdNotFound(ProgressionIdNotFoundException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(NotValidResourceException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  ErrorMsg notValidResource(NotValidResourceException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(GoldNotEnoughException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  ErrorMsg notEnoughGold(GoldNotEnoughException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(UpgradeErrorException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  ErrorMsg upgradeError(UpgradeErrorException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }

  @ResponseBody
  @ExceptionHandler(RoleNotExistsException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ErrorMsg roleNotExists(RoleNotExistsException ex) {
    return new ErrorMsg("error", ex.getMessage());
  }
}
