;;;; Lob.com Letters API
(ns lob-clj.letters
  (:require [lob-clj.models :refer [->Address ->Letter]])
  (:import [com.lob.id ZipCode]
           [com.lob.protocol.request AddressRequest, LetterRequest]
           [com.lob.protocol.response LetterResponse]
           [lob_clj.models Address, Letter]))


(defn- make-address-request [^Address addr]
  (.. AddressRequest builder
      (name (:name addr))
      (line1 (:line1 addr))
      (line2 (:line2 addr))
      (city (:city addr))
      (state (:state addr))
      (zip (ZipCode. (.toString (:zip addr))))
      (country (:country addr))
      build))

(defn- process-thumbnail-response [thumb]
  (let [getters (juxt (memfn getSmall) (memfn getMedium) (memfn getLarge))
        keys [:small :medium :large]]
    (->> (getters thumb)
         (map vector keys)
         (into {}))))

(defn- make-letter [^LetterResponse resp]
  (let [id (.getId resp)
        expected-delivery-date (.getExpectedDeliveryDate resp)
        url (.getUrl resp)
        carrier (.getCarrier resp)
        thumbnails (->> (.getThumbnails resp)
                        (map process-thumbnail-response))]
    (->Letter id expected-delivery-date url carrier thumbnails)))

(defn send-letter
  [^LobClient client
   ^Address from
   ^Address to
   ^String html-or-pdf
   ^java.util.Map content]
  (let [from-req (make-address-request from)
        to-req   (make-address-request to)
        letter-req (.. LetterRequest builder
                       (from from-req)
                       (to to-req)
                       (file html-or-pdf)
                       (color false)
                       (data content)
                       build)]
    (-> (.. client (createLetter letter-req) get)
        make-letter)))

