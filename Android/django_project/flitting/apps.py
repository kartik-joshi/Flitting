from django.apps import AppConfig


class FlittingConfig(AppConfig):
    name = 'flitting'

    def ready(self):
        import flitting.signals
