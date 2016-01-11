(ns example.handler
  (:require
   [compojure.route :as route]
   [compojure.core :refer [routes GET POST ANY]]
   [ring.util.response :refer [response content-type charset]]
   [example.db :refer [save-director<! delete-director! directors save-author<! delete-author! authors]]
   [example.api :refer [get-director-of-movie]]))

(defn directors-routes [{{db :db-spec} :db}]
  (routes
   (GET "/movies" [] "Welcome. Feed a movie title, and get the director back. Info https://github.com/danielsz/system-advanced-example")
   (POST "/movie" req (fn [{params :params :as req}]
                        (-> (pr-str {:director (get-director-of-movie (:movie params))})
                            response
                            (content-type "application/edn")
                            (charset "UTF-8"))))
   (GET "/directors" req (-> (pr-str (map :name (directors db)))
                             response
                             (content-type "application/edn")
                             (charset "UTF-8")))
   (ANY "/director" req (fn [{params :params :as req}]
                          (->
                           (case (:request-method req)
                             :put (save-director<! db (:director params))
                             :delete (delete-director! db (:director params)))
                           response)))))

(defn authors-routes [{{db :db-spec} :db}]
  (routes
   (GET "/books" [] "Welcome. Feed a book title, and get the author back. Info https://github.com/danielsz/system-advanced-example")
   (POST "/book" req (fn [{params :params :as req}]
                       (-> (pr-str {:director (get-director-of-movie (:movie params))})
                           response
                           (content-type "application/edn")
                           (charset "UTF-8"))))
   (GET "/authors" req (-> (pr-str (map :name (authors db)))
                           response
                           (content-type "application/edn")
                           (charset "UTF-8")))
   (ANY "/author" req (fn [{params :params :as req}]
                        (->
                         (case (:request-method req)
                           :put (save-author<! db (:author params))
                           :delete (delete-author! db (:author params)))
                         response)))))
