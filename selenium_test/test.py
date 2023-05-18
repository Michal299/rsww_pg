from selenium import webdriver
from selenium.common import TimeoutException
from selenium.webdriver.common.alert import Alert
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.wait import WebDriverWait
import time


# assign the appropriate value
URL = "http://localhost:3000/login"
SLEEP_TIME = 0.5


class Test:
    def __init__(self):
        delay = 10
        driver = webdriver.Chrome()
        driver.maximize_window()
        driver.get(URL)

        #log in
        time.sleep(SLEEP_TIME / 2)
        driver.find_element(By.ID, "usernameInput").send_keys("admin")
        time.sleep(SLEEP_TIME / 2)
        driver.find_element(By.ID, "passwordInput").send_keys("admin")
        login_button = driver.find_element(By.XPATH, "//button[@type='submit']")
        if login_button is not None:
            time.sleep(SLEEP_TIME / 2)
            login_button.click()

        try:
            # load offers
            WebDriverWait(driver, delay).until(EC.presence_of_element_located((By.CLASS_NAME, 'card')))
            time.sleep(SLEEP_TIME)
            offer_card = driver.find_element(
                By.XPATH, "//*[contains(@class,'card') and .//*[text()='Samira Club Spa & Aquapark']]")
            offer = offer_card.find_element(By.CLASS_NAME, "stretched-link")

            # choose offer
            driver.execute_script("arguments[0].scrollIntoView();", offer)
            time.sleep(SLEEP_TIME / 2)
            driver.execute_script("arguments[0].click();", offer)
            time.sleep(SLEEP_TIME / 2)

            # choose food options
            WebDriverWait(driver, delay).until(EC.presence_of_element_located((By.NAME, 'food-options')))
            food_options = driver.find_elements(By.NAME, "food-options")
            driver.execute_script("arguments[0].scrollIntoView();", food_options[len(food_options) - 1])
            time.sleep(SLEEP_TIME)
            driver.execute_script("arguments[0].click();", food_options[len(food_options) - 1])
            time.sleep(SLEEP_TIME / 2)

            # choose room options
            room_options = driver.find_elements(By.NAME, "room-options")
            driver.execute_script("arguments[0].scrollIntoView();", room_options[0])
            time.sleep(SLEEP_TIME / 2)
            driver.execute_script("arguments[0].click();", room_options[0])
            time.sleep(SLEEP_TIME / 2)

            # reservation
            reserve_button = driver.find_element(By.CLASS_NAME, "btn-success")
            driver.execute_script("arguments[0].scrollIntoView();", reserve_button)
            time.sleep(SLEEP_TIME)
            driver.execute_script("arguments[0].click();", reserve_button)
            time.sleep(SLEEP_TIME)
            Alert(driver).accept()
            time.sleep(SLEEP_TIME)

            # go to my reservations
            my_reservations = driver.find_element(By.CLASS_NAME, "btn-outline-success")
            driver.execute_script("arguments[0].scrollIntoView();", my_reservations)
            time.sleep(SLEEP_TIME)
            driver.execute_script("arguments[0].click();", my_reservations)
            time.sleep(SLEEP_TIME)

            # cancel reservation
            cancel_reservation = driver.find_element(By.CLASS_NAME, "btn-outline-danger")
            driver.execute_script("arguments[0].scrollIntoView();", cancel_reservation)
            time.sleep(SLEEP_TIME)
            driver.execute_script("arguments[0].click();", cancel_reservation)
            time.sleep(SLEEP_TIME)
            Alert(driver).accept()
            time.sleep(SLEEP_TIME)

            # back to main page
            main_page = driver.find_element(By.CLASS_NAME, "navbar-brand")
            driver.execute_script("arguments[0].click();", main_page)
            time.sleep(SLEEP_TIME)

            # add participants
            select_participants = driver.find_element(By.CLASS_NAME, "btn-outline-secondary")
            driver.execute_script("arguments[0].click();", select_participants)
            time.sleep(SLEEP_TIME / 2)
            add_participants = driver.find_elements(By.CLASS_NAME, "btn-outline-secondary")
            driver.execute_script("arguments[0].click();", add_participants[4])
            time.sleep(SLEEP_TIME / 2)
            driver.execute_script("arguments[0].click();", add_participants[6])
            time.sleep(SLEEP_TIME / 2)
            driver.execute_script("arguments[0].click();", add_participants[6])
            time.sleep(SLEEP_TIME)

            # choose destination
            WebDriverWait(driver, delay).until(EC.presence_of_element_located((By.XPATH, "//select[1]")))
            select_destination = Select(driver.find_element(By.XPATH, "//select[1]"))
            time.sleep(SLEEP_TIME / 2)
            select_destination.select_by_visible_text('Egipt')
            time.sleep(SLEEP_TIME)

            # choose departure place
            WebDriverWait(driver, delay).until(EC.presence_of_element_located((By.XPATH, "//select[@id='departurePlaceInput']")))
            select_departure_place = Select(driver.find_element(By.XPATH, "//select[@id='departurePlaceInput']"))
            time.sleep(SLEEP_TIME / 2)
            select_departure_place.select_by_visible_text('Warszawa (WAW)')

        except TimeoutException:
            print("Loading took too much time!")

        time.sleep(4 * SLEEP_TIME)
        driver.close()


if __name__ == "__main__":
    Test()
