(ns moodcast.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :name
 (fn [db]
   (reaction (:name @db))))

(re-frame/register-sub
 :active-panel
 (fn [db _]
   (reaction (:active-panel @db))))

(re-frame/register-sub
 :connected?
 (fn [db _]
   (reaction (:connected? @db))))

(re-frame/register-sub
 :people
 (fn [db _]
   (reaction (vals (:people @db)))))

(re-frame/register-sub
 :person
 (fn [db [_ id]]
   (reaction (get-in @db [:people id]))))

(re-frame/register-sub
 :avatars
 (fn [db _]
   (reaction (:avatars @db))))

(re-frame/register-sub
 :avatar
 (fn [db [_ id]]
   (reaction (get (:avatars @db) id))))

(re-frame/register-sub
 :background
 (fn [db [_ id]]
   (reaction (get (:backgrounds @db) id))))

(re-frame/register-sub
 :scroll
 (fn [db _]
   (reaction (:scroll @db))))
