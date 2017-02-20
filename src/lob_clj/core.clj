(ns lob-clj.core
  (:import [com.lob Lob]
           [com.lob.client AsyncLobClient, LobClient]))

;; Precaution, and also lets us test with newer versions (by varying the string)
(Lob/setApiVersion "2016-06-30")

(defn make-client [^String api-key]
  (. AsyncLobClient (createDefault api-key)))
