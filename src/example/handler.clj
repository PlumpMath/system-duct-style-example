(ns example.handler
  (:require
   [compojure.route :as route]
   [compojure.core :refer [routes GET POST ANY]]
   [ring.util.response :refer [response content-type charset]]
   [example.db :refer [save-director<! delete-director! directors save-author<! delete-author! authors]]
   [example.api :refer [get-director-of-movie]]))

(defn directors-routes [{{db :db-spec} :db}]
  (routes
   (GET "/movies" [] "Welcome. Feed a movie title, and get the director back.")
   (POST "/movie" req (fn [{params :params :as req}]
                        (-> (pr-str {:director (get-director-of-movie (:movie params))})
                            response
                            (content-type "application/edn")
                            (charset "UTF-8"))))
   (GET "/directors" req (-> (pr-str (map :name (directors {} {:connection db})))
                             response
                             (content-type "application/edn")
                             (charset "UTF-8")))
   (ANY "/director" req (fn [{params :params :as req}]
                          (->
                           (case (:request-method req)
                             :put (save-director<! {:name (:director params)} {:connection db})
                             :delete (delete-director! {:name (:director params)} {:connection db}))
                           response)))))

(defn authors-routes [{{db :db-spec} :db}]
  (routes
   (GET "/books" [] "Welcome. Feed a book title, and get the author back.")
   (POST "/book" req (fn [{params :params :as req}]
                       (-> (pr-str {:director (get-director-of-movie (:movie params))})
                           response
                           (content-type "application/edn")
                           (charset "UTF-8"))))
   (GET "/authors" req (-> (pr-str (map :name (authors {} {:connection db})))
                           response
                           (content-type "application/edn")
                           (charset "UTF-8")))
   (ANY "/author" req (fn [{params :params :as req}]
                        (->
                         (case (:request-method req)
                           :put (save-author<! {:name (:author params)} {:connection db})
                           :delete (delete-author! {:name (:author params)} {:connection db}))
                         response)))))
