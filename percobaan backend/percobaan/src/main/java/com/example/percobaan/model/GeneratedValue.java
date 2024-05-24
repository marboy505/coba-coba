package com.example.percobaan.model;

import javax.persistence.GenerationType;

public @interface GeneratedValue {
    GenerationType strategy();
}
