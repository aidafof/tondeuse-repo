package com.mowitnow.exception;

import lombok.Getter;

/**
 * enum class referential for the specific business errors raised in the application
 */
@Getter
public enum ErrorCode {
   INVALID_ORIENTATION("Invalid orientation code {0}"),
   INVALID_INSTRUCTION("Invalid instruction code {0}"),
   INVALID_COORDINATES("Invalid coordinates Type %s"),
   MISSING_LAWN_DATA ("missing lawn data: lawn coordinates are missing"),
   MISSING_MOWER_DATA ("missing mower data: position and instruction lines are missing"),
   INVALID_LINE_NUMBER("Inconsistent line count-%s"),
   INVALID_POSITION_FORMAT("Invalid position/orientation line format %s"),
   INVALID_INSTRUCTION_FORMAT ("Invalid instruction line format %s"),

   IOException_Error("IOException :%s ");

   private final String  label;

   ErrorCode(String label){
      this.label = label;
   }

}
