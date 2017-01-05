from abc import ABCMeta, abstractmethod

class actualFareCalculate(metaclass=ABCMeta):
    @abstractmethod
    #
    def getActualFare(self, startTime, endTime, locationArray):
        pass

    @abstractmethod
    def showActualFare(self, ActualFare):
        pass


