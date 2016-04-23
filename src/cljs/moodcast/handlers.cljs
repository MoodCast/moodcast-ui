(ns moodcast.handlers
    (:require [re-frame.core :as re-frame]
              [moodcast.db :as db]
              [moodcast.util :refer [index-by]]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/register-handler
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/register-handler
 :connected
 (fn [db [_]]
   (assoc-in db [:connected?] true)))

(re-frame/register-handler
 :disconnected
 (fn [db [_]]
   (dissoc db :connected?)))

(re-frame/register-handler
 :load-background
 (fn [db [_ id svg]]
   (assoc-in db [:backgrounds id] svg)))

(re-frame/register-handler
 :load-svg
 (fn [db [_ name item state svg]]
   (assoc-in db [:avatars name item state] svg)))

(re-frame/register-handler
 :load-face
 (fn [db [_ id filename]]
   (println "LOAD FACE" id filename)
   (update-in db [:people id :face] (fn [_] filename))))

(re-frame/register-handler
 :message
 (fn [db [_ msg]]
   db))

(re-frame/register-handler
 :state-change
 (fn [db [_ id state]]
   (let [person (get-in db [:people id])]
     (println "STATE-CHANGE" id person)
     (if (not (:avatar person))
       (let [position #_[(/ 1920 2) (/ 1080 2)] [(+ 300 (rand-int 300)) (- 1080 200 (rand-int 300))]]
         (update-in db [:people id] (fn [x] (merge x {:id id :avatar (case (rand-int 2)
                                                                       0 :sharkman
                                                                       1 :ironman) :state state :position position}))))
       (update-in db [:people id] (fn [x] (assoc x :state state)))))))

(re-frame/register-handler
 :scroll
 (fn [db [_ y]]
   (assoc db :scroll y)))
