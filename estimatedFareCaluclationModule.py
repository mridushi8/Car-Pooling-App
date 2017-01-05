from abc import ABCMeta, abstractmethod


class estimateFareCalculate(metaclass=ABCMeta):
    @abstractmethod


    def getEstimateFare(self,startPoint, endPoint):
        pass

    @abstractmethod
    def showEstimateFare(self,estimateFare):
        pass


