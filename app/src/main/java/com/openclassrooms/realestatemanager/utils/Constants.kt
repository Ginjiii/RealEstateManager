package com.openclassrooms.realestatemanager.utils

import android.Manifest

// DEFAULT VALUE
const val MAX_VALUE = 999999999999.99
const val MIN_VALUE = 0.0
const val IMAGE_ONLY_TYPE = "image/*"

const val SHARED_PREFERENCES_USERNAME = "username"

//DATE FORMAT
const val DATE_FORMAT_FOR_NAME = "yyyyMMdd_HHmmss"


//RC_CODE
const val RC_IMAGE_PERMS = 100
const val RC_CHOOSE_PHOTO = 101
const val PICK_DATE_DIALOG_CODE = 102
const val AGENT_LIST_DIALOG_CODE = 103
const val RC_CODE_ADD_AGENT = 104
const val RC_CODE_ADD_PROPERTY = 105
const val RC_CODE_MODIFY_PROPERTY = 109
const val RC_LOCATION_PERMS = 106
const val RC_CODE_TAKE_PHOTO = 107
const val RC_CODE_DETAIL_PROPERTY = 108

//ACTION
const val ACTION_TYPE_ADD_PROPERTY = "ActionTypeAddProperty"
const val ACTION_TYPE_LIST_PROPERTY = "ActionTypeListProperty"

// TABLE NAME
const val AGENT_TABLE_NAME = "agents"
const val PROPERTY_TABLE_NAME = "properties"
const val PHOTO_TABLE_NAME = "pictures"

// PERMISSIONS
const val PERMS_LOCALISATION = Manifest.permission.ACCESS_FINE_LOCATION
const val PERMS_EXT_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE