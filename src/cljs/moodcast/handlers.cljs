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
 :load-svg
 (fn [db [_ name svg]]
   (assoc-in db [:svgs name] svg)))

(re-frame/register-handler
 :message
 (fn [db [_ msg]]
   db))

(re-frame/register-handler
 :person
 (fn [db [_ p]]
   (assoc-in db [:people (:id p)] p)))

(re-frame/register-handler
 :scroll
 (fn [db [_ y]]
   (assoc db :scroll y)))
