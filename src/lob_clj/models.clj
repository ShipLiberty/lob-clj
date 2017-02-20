(ns lob-clj.models
  (:gen-class))

;; Incomplete representations of https://lob.com/docs#addresses_object and https://lob.com/docs#letters_object
(defrecord Address [id name line1 line2 city state zip country])
(defrecord Letter [id expected-delivery-date url carrier thumbnails])
