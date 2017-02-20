(ns lob-clj.core
  (:import [com.lob Lob]
           [com.lob.client AsyncLobClient, LobClient]))

(defn make-client [^String api-key]
  ;; Precaution, and also lets us test with newer versions (by varying the string)
  (Lob/setApiVersion "2016-06-30")
  (. AsyncLobClient (createDefault api-key)))
